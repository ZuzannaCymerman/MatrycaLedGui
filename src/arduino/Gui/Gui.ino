
#include "SoftwareSerial.h"
#include "WiFiEsp.h"
#include "Views.h"

SoftwareSerial EspSerial(10, 11);
WiFiEspServer server(80);
int status = WL_IDLE_STATUS;
 
void setup() {
  
  Serial.begin(9600);
  EspSerial.begin(9600);
  
  WiFi.init(&EspSerial);
  
  status = WiFi.beginAP("LedMatrix");
  
  IPAddress local_IP(192, 168, 0, 190);
  WiFi.configAP(local_IP);
  Serial.println(WiFi.localIP());
  server.begin();
}

void loop() {

   WiFiEspClient client = server.available();
   int *ledNumbers;
   int *ledColors;

 if (client)
    {
        Serial.println("A client has connected");

        while (client.connected())
        {
            if (client.available())
            {
              Serial.println("connected");
              client.readStringUntil('|');
              String action = client.readStringUntil('|');

              Serial.println(action);
              
              if(action == "V"){

                 int ledQuantity = receiveLedQuantity(client);
                  
                 ledNumbers = new int[ledQuantity];
                 ledColors = new int[ledQuantity];    
                
                 receiveView(ledNumbers, ledColors, client, ledQuantity);
  
                 Serial.println(ledNumbers[2]);
                 
               }else if (action == "S"){
                
               }
             
                client.print(
                      "HTTP/1.1 200 OK\r\n"
                      "Connection: close\r\n"
                      "\r\n");
                     

                  client.stop();
                

                }
            }

        delay(10);
        client.stop();
        Serial.println("Client disconnected");
      }
     
}
           

  
