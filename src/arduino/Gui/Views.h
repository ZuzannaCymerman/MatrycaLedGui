#ifndef View_H
#define Views_H
#include "WiFiEsp.h"
#include <Adafruit_NeoPixel.h>

void showView(char *ledNumbers, char (*ledColors)[3],Adafruit_NeoPixel &pixels);
void getRGB(char ledColor, char *RGB);
void clientStop(WiFiEspClient client);
#endif
