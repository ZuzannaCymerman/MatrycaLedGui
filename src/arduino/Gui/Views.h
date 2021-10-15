#ifndef View_H
#define Views_H
#include "WiFiEsp.h"
#include <Adafruit_NeoPixel.h>

void receiveView(int ledNumbers[], int ledColors[], WiFiEspClient client, int ledQuantity);
int receiveLedQuantity(WiFiEspClient client);
void showView(int ledNumbers[], int ledColors[], Adafruit_NeoPixel &pixels, int ledQuantity);
void getRGB(int ledColor, int RGB[]);
void stopView(Adafruit_NeoPixel &pixels);
void setPixel(int ledColor, int led, Adafruit_NeoPixel &pixels);
void clientStop(WiFiEspClient client);
#endif
