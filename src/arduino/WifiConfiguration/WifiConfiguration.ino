
#include "SoftwareSerial.h"
#include "WiFiEsp.h"
#include "ArduinoJson.h"
#include "EEPROM.h"
#include "WifiConfiguration.h"

SoftwareSerial EspSerial(10, 11);
WiFiEspServer server(80);
int wifi_status;

#define red 12
#define green 13
#define yellow 9
#define test 8
 
void setup() {
  pinMode(red, OUTPUT); 
  pinMode(green, OUTPUT);
  pinMode(yellow, OUTPUT);
  pinMode(test, OUTPUT);

  digitalWrite(yellow, HIGH);
  
  Serial.begin(9600);
  EspSerial.begin(9600);
  
  WiFi.init(&EspSerial);
  wifi_status = setupWiFi(wifi_status);
  
  while(!WiFiworks(wifi_status)){
    
    digitalWrite(yellow, LOW);
    digitalWrite(green, LOW);
    digitalWrite(red, HIGH);
     
    while(Serial.available() == 0){}

    digitalWrite(red, LOW);
    digitalWrite(yellow, HIGH);

    String ssid = Serial.readStringUntil('|');
    String password = Serial.readStringUntil('|');    

    clearEEPROM();
    writeStringToEEPROM(0, ssid);
    writeStringToEEPROM(20, password);

    wifi_status = setupWiFi(wifi_status);
    
  }
  if(WiFiworks(wifi_status)){
    digitalWrite(yellow, LOW);
    digitalWrite(red, LOW);
    digitalWrite(green, HIGH);
  }
  IPAddress local_IP(192, 168, 0, 190);
  WiFi.config(local_IP);
  Serial.println(WiFi.localIP());
  
  server.begin();
}

void loop() {
String state;
WiFiEspClient client = server.available();

  if (client){
    digitalWrite(yellow, HIGH);
     while (client.connected()){
      
        if (client.available()){
  
          client.readStringUntil('{');
          String json_data = client.readStringUntil('}');
          Serial.println(json_data);

          //state = jsonHashValue(json_data, "led");
          Serial.println(json_data);
          
          client.print(
              "HTTP/1.1 200 OK\r\n"
              "Connection: close\r\n"
              "\r\n");
              
          client.stop();
          digitalWrite(yellow, LOW);
      }
      
     }
    }

  if(state == "on"){        
    digitalWrite(test, HIGH);             
  }
  else if(state =="off"){
    digitalWrite(test, LOW);   
  }
           

  
}
