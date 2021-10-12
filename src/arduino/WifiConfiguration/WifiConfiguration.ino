
#include "SoftwareSerial.h"
#include "WiFiEsp.h"
#include "ArduinoJson.h"
#include "EEPROM.h"
#include "WifiConfiguration.h"

SoftwareSerial EspSerial(10, 11);
WiFiEspServer server(80);
int wifi_status;
int* ledNumbers;
int* ledColors;


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
        while (client.available()){
          
          client.readStringUntil('|');
          String dataType = client.readStringUntil('|');
          String ledQuantityStr = client.readStringUntil('[');
          String data = client.readStringUntil(']');

           const char * ledQuantityChar = ledQuantityStr.c_str();
           int ledQuantity = atoi(ledQuantityChar);

          data = "[" + data + "]";

          Serial.println(data);
          Serial.println(ledQuantity);
          Serial.println(dataType);
          
          const size_t CAPACITY = JSON_ARRAY_SIZE(ledQuantity)+1000;
          DynamicJsonDocument doc(CAPACITY);
          deserializeJson(doc, data);

          JsonArray array = doc.as<JsonArray>();
          
          for(JsonVariant v : array) {
              Serial.println(v.as<int>());
          }

          if(dataType == "N"){
            ledNumbers = new int[ledQuantity];
            for(int i=0;i<ledQuantity;i++){
              ledNumbers[i] = array[i].as<int>();
            }
          }
          
          else if(dataType == "C"){
            ledColors = new int[ledQuantity];
            for(int i=0;i<ledQuantity;i++){
              ledColors[i] = array[i].as<int>();
            }
          }
          
            Serial.println(ledNumbers[2]);
            Serial.println(ledColors[2]);

          client.print(
              "HTTP/1.1 200 OK\r\n"
              "Connection: close\r\n"
              "\r\n");
            client.stop();
          }
          digitalWrite(yellow, LOW);
          client.stop();
      }
      client.stop();  
    } 


 

  if(state == "on"){        
    digitalWrite(test, HIGH);             
  }
  else if(state =="off"){
    digitalWrite(test, LOW);   
  }
           

  
}
