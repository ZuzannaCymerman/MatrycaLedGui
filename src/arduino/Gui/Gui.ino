
#include "SoftwareSerial.h"
#include "WiFiEsp.h"
#include "Views.h"

#define LED_COUNT 200
#define LED_PIN 45

SoftwareSerial EspSerial(10, 11);
WiFiEspServer server(80);
int status = WL_IDLE_STATUS;
Adafruit_NeoPixel pixels(LED_COUNT,LED_PIN, NEO_RGB+NEO_KHZ800);

String action;
boolean stop = true;

char ledColors[200][3];
char RGB[3];
 
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

    if(!stop){
       showView(ledColors, pixels);
    }

    WiFiEspClient client = server.available();
    if (client)
      {
         Serial.println("A client has connected");
  
          while (client.connected()){
            
              if (client.available()){
               
                client.readStringUntil('|');
                action = client.readStringUntil('|');
  
                Serial.println(action);
                
                if(action == "V"){
                  stop = false;
                  
                 receiveView(ledColors, RGB, client);

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
           

  
