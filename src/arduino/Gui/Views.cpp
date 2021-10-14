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
  clientStop(client);
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
      Serial.print(ledNumbers[i]);
      setPixel(ledColors[i],ledNumbers[i], pixels);
  }
  Serial.println(" show");
 
}

void stopView(Adafruit_NeoPixel &pixels){
  pixels.clear();
}

void setPixel(int ledColor, int led, Adafruit_NeoPixel &pixels){
  switch(ledColor){
    case 1:
      //red
      pixels.setPixelColor(led, pixels.Color(255, 0, 0));
      pixels.show();
      break;
    case 2:
      //green
      pixels.setPixelColor(led, pixels.Color(0, 255, 0));
      pixels.show();    
      break;
    case 3:
      //blue
      pixels.setPixelColor(led, pixels.Color(0, 0, 255));
      pixels.show();  
      break;
    case 4:
      //yellow
      pixels.setPixelColor(led, pixels.Color(255, 255, 0));
      pixels.show();  
      break;
    case 5:
      //orange
      pixels.setPixelColor(led, pixels.Color(255, 200, 0));
      pixels.show();  
      break;
    case 6:
      //cyan
      pixels.setPixelColor(led, pixels.Color(0, 255, 255));
      pixels.show();  
      break;
    case 7:
      //white
      pixels.setPixelColor(led, pixels.Color(255, 255, 255));
      pixels.show();  
      break;
    case 8:
      //magenta
      pixels.setPixelColor(led, pixels.Color(255, 0, 255));
      pixels.show();  
      break;
    case 9:
      //pink
      pixels.setPixelColor(led, pixels.Color(255, 175, 175));
      pixels.show();  
      break;
  }    
}

void clientStop(WiFiEspClient client){
  client.print(
              "HTTP/1.1 200 OK\r\n"
              "Connection: close\r\n"
              "\r\n");
             
  client.stop();
}
 
