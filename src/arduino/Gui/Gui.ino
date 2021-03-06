
#include "SoftwareSerial.h"
#include "WiFiEsp.h"
#include "Views.h"

#define LED_COUNT 30
#define LED_PIN 12

SoftwareSerial EspSerial(10, 11);
WiFiEspServer server(80);
int status = WL_IDLE_STATUS;
Adafruit_NeoPixel pixels(LED_COUNT,LED_PIN, NEO_RGB+NEO_KHZ800);

String action;
boolean stop = true;

char ledColors[200][3];
int ledBrightness;
 
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
  
  pixels.begin();
  pixels.clear();
 
}

void loop() { 

    if(!stop){
      
       showView(ledColors,ledBrightness, pixels);
    }

    WiFiEspClient client = server.available();
    if (client)
      {
         Serial.println("A client has connected");
  
          while (client.connected()){
            
              if (client.available()){
                pixels.clear();
                client.readStringUntil('|');
                action = client.readStringUntil('|');
  
                Serial.println(action);
                
                if(action == "V"){
                  stop = false;
                  
                 ledBrightness = receiveView(ledColors,client);
                 Serial.print(ledBrightness);

                  Serial.println("Data collected");
                  
                  clientStop(client);
                   
                 }else if (action == "S"){ 
                    stop = true;                   
                    pixels.clear();
                    Serial.println("stop");
                    clientStop(client); 
                   
                 }
               
              }
           }
        } 
 
     
}
           

  
