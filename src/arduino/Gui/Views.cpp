#include "Views.h"
#include "WiFiEsp.h"
#include <Adafruit_NeoPixel.h>

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

void showView(int ledNumbers[], int ledColors[], Adafruit_NeoPixel &pixels, int ledQuantity){
  pixels.clear();

  for(int i=0; i<ledQuantity; i++) {
    int RGB[3];
    
    getRGB(ledColors[i], RGB);
    
    int R = RGB[0];
    int G = RGB[1];
    int B = RGB[2];

    if((R!= NULL)&&(G!=NULL)&&(B!=NULL)){
      pixels.setPixelColor(ledNumbers[i], pixels.Color(R, G, B));
      pixels.show();
    }
  }
  Serial.println("show");
}

void stopView(Adafruit_NeoPixel &pixels){
  pixels.clear();
}


void getRGB(int ledColor, int RGB[]){
  switch(ledColor){
    case 1:
      //red
      RGB[0] = 255;
      RGB[1] = 0;
      RGB[2] = 0;
      break;
    case 2:
      //green
      RGB[0] = 0;
      RGB[1] = 255;
      RGB[2] = 0;    
      break;
    case 3:
      //blue
      RGB[0] = 0;
      RGB[1] = 0;
      RGB[2] = 255;  
      break;
    case 4:
      //yellow
      RGB[0] = 255;
      RGB[1] = 255;
      RGB[2] = 0;  
      break;
    case 5:
      //orange
      RGB[0] = 255;
      RGB[1] = 200;
      RGB[2] = 0;  
      break;
    case 6:
      //cyan
      RGB[0] = 0;
      RGB[1] = 255;
      RGB[2] = 255;  
      break;
    case 7:
      //white
      RGB[0] = 255;
      RGB[1] = 255;
      RGB[2] = 255;  
      break;
    case 8:
      //magenta
      RGB[0] = 255;
      RGB[1] = 0;
      RGB[2] = 255;  
      break;
    case 9:
      //pink
      RGB[0] = 255;
      RGB[1] = 175;
      RGB[2] = 175;  
      break;
  }    
}
