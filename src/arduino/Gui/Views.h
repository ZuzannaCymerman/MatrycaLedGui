#ifndef View_H
#define Views_H
#include "WiFiEsp.h"

void receiveView(int ledNumbers[], int ledColors[], WiFiEspClient client, int ledQuantity);
int receiveLedQuantity(WiFiEspClient client);
#endif
