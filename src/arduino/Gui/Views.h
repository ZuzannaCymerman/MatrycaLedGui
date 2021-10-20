#ifndef View_H
#define Views_H
#include "WiFiEsp.h"
#include <Adafruit_NeoPixel.h>

void showView(char (*ledColors)[3],int ledBrigthness, Adafruit_NeoPixel &pixels);
int receiveView(char (*ledColors)[3], WiFiEspClient client);
void getRGB(char ledColor, char *RGB);
void clientStop(WiFiEspClient client);
#endif
