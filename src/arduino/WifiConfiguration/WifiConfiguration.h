#ifndef WifiConfiguration_H
#define WifiConfiguration_H

#include <Arduino.h>

void writeStringToEEPROM(int addrOffset, const String &strToWrite);
char* readStringFromEEPROM(int addrOffset);
void clearEEPROM();
bool WiFiworks(int status);
int setupWiFi(int wifi_status);
String jsonHashValue(String json_data, String key);

#endif
