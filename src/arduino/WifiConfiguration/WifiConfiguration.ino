
#include "SoftwareSerial.h"
#include "WiFiEsp.h"
#include "ArduinoJson.h"
#include "EEPROM.h"

SoftwareSerial EspSerial(10, 11);
WiFiEspServer server(80);
int wifi_status;

#define red 12
#define green 13
#define yellow 9
#define test 8


void writeStringToEEPROM(int addrOffset, const String &strToWrite)
{
  byte len = strToWrite.length();
  EEPROM.write(addrOffset, len);
  for (int i = 0; i < len; i++)
  {
    EEPROM.write(addrOffset + 1 + i, strToWrite[i]);
  }
}

char* readStringFromEEPROM(int addrOffset)
{
  int newStrLen = EEPROM.read(addrOffset);
  char *data = malloc(newStrLen + 1);
  for (int i = 0; i < newStrLen; i++)
  {
    data[i] = EEPROM.read(addrOffset + 1 + i);
  }
  data[newStrLen] = '\0';
   
  return data;
}

void clearEEPROM(){
   for (int i = 0 ; i < EEPROM.length() ; i++) {
    EEPROM.write(i, 0);
  }
}

bool WiFiworks(int status){
  if(status == 1){
    return true;
  }
  else{
    return false;
  }
  
}

int setupWiFi(){

  int status = WL_IDLE_STATUS;
  
  const char* ssid = readStringFromEEPROM(0);
  const char* password = readStringFromEEPROM(20);

   while (status != WL_CONNECTED){
      wifi_status = WiFi.begin(ssid, password);
      break;
    }
    
   return wifi_status;
}

String jsonHashValue(String json_data, String key){
  String value;
  
  json_data = "{" + json_data + "}";
  
  if(json_data.indexOf('{', 0) >= 0){
    const size_t bufferSize = JSON_OBJECT_SIZE(1) + 20;
    DynamicJsonDocument doc(bufferSize);
    DeserializationError err = deserializeJson(doc, json_data);
    const char* char_data = doc[key];
    String value = String(char_data); 
    return value; 
  } 
}
      
void setup() {
  pinMode(red, OUTPUT); 
  pinMode(green, OUTPUT);
  pinMode(yellow, OUTPUT);
  pinMode(test, OUTPUT);

  digitalWrite(yellow, HIGH);
  
  Serial.begin(9600);
  EspSerial.begin(9600);
  
  WiFi.init(&EspSerial);


  wifi_status = setupWiFi();
  
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

    wifi_status = setupWiFi();
    
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

          state = jsonHashValue(json_data, "led");
          
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
