#include "Views.h"
#include "WiFiEsp.h"

void receiveView(int ledNumbers[], int ledColors[], WiFiEspClient client, int ledQuantity){

  for(int i=0;i<ledQuantity;i++){
    String dataStr = client.readStringUntil('|');
    const char * dataChar = dataStr.c_str();
    ledNumbers[i] = atoi(dataChar);
  }
  
  for(int i=0;i<ledQuantity;i++){
    String dataStr = client.readStringUntil('|');
    const char * dataChar = dataStr.c_str();
    ledColors[i] = atoi(dataChar);
  }
}

int receiveLedQuantity(WiFiEspClient client){
   String ledQuantityStr = client.readStringUntil('|');
   const char * ledQuantityChar = ledQuantityStr.c_str();
   int ledQuantity = atoi(ledQuantityChar);

   return ledQuantity;
}

void showView(int ledNumbers[], int ledColors[]){
  
}
