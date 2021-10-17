#ifndef View_H
#define Views_H
#include "WiFiEsp.h"
#include <Adafruit_NeoPixel.h>

void showView(char (*ledColors)[3],Adafruit_NeoPixel &pixels);
void receiveView(char (*ledColors)[3], char *RGB, WiFiEspClient client);
void getRGB(char ledColor, char *RGB);
void clientStop(WiFiEspClient client);
#endif
