#include "WifiConfiguration.h"
#include "SoftwareSerial.h"
#include "WiFiEsp.h"
#include "ArduinoJson.h"
#include "EEPROM.h"


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

int setupWiFi(int wifi_status){
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
      
