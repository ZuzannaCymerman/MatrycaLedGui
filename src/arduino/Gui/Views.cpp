#include "Views.h"
#include "WiFiEsp.h"
#include <Adafruit_NeoPixel.h>

void receiveView(char *ledNumbers, char (*ledColors)[3], char *RGB, WiFiEspClient client){
   for(int i=0;i<200;i++){
        char data = client.read();
        ledNumbers[i] = data;
      }
      
      for(int i=0;i<200;i++){
        char data = client.read();
        getRGB(data, RGB);
        ledColors[i][0] = RGB[0];
        ledColors[i][1] = RGB[1];
        ledColors[i][2] = RGB[2];  
    }
}

void showView(char *ledNumbers,char (*ledColors)[3], Adafruit_NeoPixel &pixels){
  pixels.clear();
  int offset = 128;

  for(int i=0; i<200; i++) {
      if(ledNumbers[i] == '1'){
       int R = int(ledColors[i][0]+offset);
       int G = int(ledColors[i][1]+offset);
       int B = int(ledColors[i][2]+offset);
      pixels.setPixelColor(i, pixels.Color(R, G, B));
      pixels.show(); 
      }     
  }
  Serial.println(" show");
} 

void getRGB(char ledColor, char *RGB){
int  offset = 128;
  switch(ledColor){
    case '1':
      RGB[0] = char(255-offset);
      RGB[1] = char(0-offset);
      RGB[2] = char(0-offset);
      break;
    case '2':
      //green
      RGB[0] = char(0-offset);
      RGB[1] = char(255-offset);
      RGB[2] = char(0-offset);  
      break;
    case '3':
      //blue
      RGB[0] = char(0-offset);
      RGB[1] = char(0-offset);
      RGB[2] = char(255-offset);
      break;
    case '4':
      //yellow
      RGB[0] = char(255-offset);
      RGB[1] = char(255-offset);
      RGB[2] = char(0-offset);
      break;
    case '5':
      //orange
      RGB[0] = char(255-offset);
      RGB[1] = char(200-offset);
      RGB[2] = char(0-offset);
      break;
    case '6':
      //cyan
      RGB[0] = char(0-offset);
      RGB[1] = char(255-offset);
      RGB[2] = char(255-offset);
      break;
    case '7':
      //white
      RGB[0] = char(255-offset);
      RGB[1] = char(255-offset);
      RGB[2] = char(255-offset);
      break;
    case '8':
      //magenta
      RGB[0] = char(255-offset);
      RGB[1] = char(0-offset);
      RGB[2] = char(255-offset);
      break;
    case '9':
      //pink
      RGB[0] = char(255-offset);
      RGB[1] = char(175-offset);
      RGB[2] = char(175-offset);
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
 
