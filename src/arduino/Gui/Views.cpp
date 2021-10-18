#include "Views.h"
#include "WiFiEsp.h"
#include <Adafruit_NeoPixel.h>

const int offset = 128;

void receiveView(char (*ledColors)[3], WiFiEspClient client){      
   for(int i=0;i<200;i++){
        char data = client.read();
        getRGB(data, ledColors[i]);
    }
}

void showView(char (*ledColors)[3], Adafruit_NeoPixel &pixels){
  pixels.clear();

  for(int i=0; i<200; i++) {
       int R = int(ledColors[i][0]+offset);
       int G = int(ledColors[i][1]+offset);
       int B = int(ledColors[i][2]+offset);
      if((R+G+B)!=0){       
        pixels.setPixelColor(i, pixels.Color(R, G, B));
        pixels.show(); 
      } 
  }
  Serial.println(" show");
} 

void getRGB(char ledColor, char *RGB){
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
    case '0':
      //off
      RGB[0] = char(0-offset);
      RGB[1] = char(0-offset);
      RGB[2] = char(0-offset);
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

 
