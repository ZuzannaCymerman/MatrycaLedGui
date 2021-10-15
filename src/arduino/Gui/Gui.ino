
#include "SoftwareSerial.h"
#include "WiFiEsp.h"
#include "Views.h"
#include "VirtualWire.h"

#define LED_COUNT 200
#define LED_PIN 45

SoftwareSerial EspSerial(10, 11);
WiFiEspServer server(80);
int status = WL_IDLE_STATUS;
Adafruit_NeoPixel pixels(LED_COUNT,LED_PIN, NEO_RGB+NEO_KHZ800);
String action;
boolean run;
boolean stop = true;
int *ledNumbers;
int *ledColors;
int ledQuantity;
 
void setup() {
  Serial.begin(9600);
  EspSerial.begin(9600);
  pinMode(LED_PIN, OUTPUT);

  WiFi.init(&EspSerial);
  
  status = WiFi.beginAP("LedMatrix");
  
  IPAddress local_IP(192, 168, 0, 190);
  WiFi.configAP(local_IP);
  Serial.println(WiFi.localIP());
  
  server.begin();
}

void loop() {

run = true;
 
  while(run){
    if(!stop){
       showView(ledNumbers,ledColors, pixels, ledQuantity);
    }

    WiFiEspClient client = server.available();
    if (client)
      {
          Serial.println("A client has connected");
  
          while (client.connected()){
            
              if (client.available()){
                
                Serial.println("connected");
                client.readStringUntil('|');
                action = client.readStringUntil('|');
  
                Serial.println(action);
                
                if(action == "V"){
                      delete[] ledNumbers;
                      delete[] ledColors;
      
                  stop = false;
                  
                   ledQuantity = receiveLedQuantity(client);
                    
                   ledNumbers = new int[ledQuantity];
                   ledColors = new int[ledQuantity];    
                  
                   receiveView(ledNumbers, ledColors, client, ledQuantity);
                    clientStop(client);
    
                   Serial.println(ledNumbers[2]);
                   
                 }else if (action == "S"){ 
                    stop = true;                   
                    pixels.clear();
                    Serial.println("stop");
                    clientStop(client); 
                   
                 }
               
               }
            }
              clientStop(client);  
        } 
  }
 
     
}
           

  
