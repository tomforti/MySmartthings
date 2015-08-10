/**
 *  8 way Relay
 *
 *  Copyright 2015 Tom Forti
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Fan Relay App",
    namespace: "Fan Control",
    author: "Tom Forti",
    description: "Arduino Relay",
    category: "",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Connect these virtual Fans to the Arduino") {
		input "fan1", title: "Fan 1 for arduino", "capability.switchLevel"
        input "fan2", title: "Fan 2 for arduino", "capability.switchLevel", required: false
        input "fan3", title: "Fan 3 for arduino", "capability.switchLevel", required: false
        input "fan4", title: "Fan 4 for arduino", "capability.switchLevel", required: false 
	}
    section("Which Arduino relay board to control?") {
		input "arduino", "device.ArduinoFans"
    }    
}

def installed() {
	subscribe(fan1, "switch.setLevel", fan1val)
    subscribe(fan1, "switch", fan1val)
    subscribe(fan2, "switch.setLevel", fan2val)
    subscribe(fan2, "switch", fan2val)
    subscribe(fan3, "switch.setLevel", fan3val)
    subscribe(fan3, "switch", fan3val)
    subscribe(fan4, "switch.setLevel", fan4val)
    subscribe(fan4, "switch", fan4val)
}

def updated() {
	unsubscribe()
	subscribe(fan1, "switch.setLevel", fan1val)
    subscribe(fan1, "switch", fan1val)
    subscribe(fan2, "switch.setLevel", fan2val)
    subscribe(fan2, "switch", fan2val)
    subscribe(fan3, "switch.setLevel", fan3val)
    subscribe(fan3, "switch", fan3val)
    subscribe(fan4, "switch.setLevel", fan4val)
    subscribe(fan4, "switch", fan4val)
    log.info "subscribed to all of Fan events"
}


def fan1val(evt)
{
	if ((evt.value == "on") || (evt.value == "off" ))
		return
	log.debug "UpdateLevel: $evt"
	int level = fan1.currentValue("level")
	log.debug "level: $level"	
    
    if (level == 0) {
    	arduino.fan1off()
    	
    }
     if (level == 30) {
    	arduino.fan1low()
    	
    }
     if (level == 60) {
    	arduino.fan1med()
    	
    }
     if (level == 100) {
    	arduino.fan1hi()
    	
    }
}


def fan2val(evt)
{
	if ((evt.value == "on") || (evt.value == "off" ))
		return
	log.debug "UpdateLevel: $evt"
	int level = fan2.currentValue("level")
	log.debug "level: $level"
    
    if (level == 0) {
    	arduino.fan2off()
    	
    }
     if (level == 30) {
    	arduino.fan2low()
    	
    }
     if (level == 60) {
    	arduino.fan2med()
    	
    }
     if (level == 100) {
    	arduino.fan2hi()
    	
    }
}

def fan3val(evt)
{
	if ((evt.value == "on") || (evt.value == "off" ))
		return
	log.debug "UpdateLevel: $evt"
	int level = fan3.currentValue("level")
	log.debug "level: $level"
    
    if (level == 0) {
    	arduino.fan3off()
    	
    }
     if (level == 30) {
		arduino.fan3low()
    	
    }
     if (level == 60) {
    	arduino.fan3med()
    	
    }
     if (level == 100) {
    	arduino.fan3hi()
    
    }
}


def fan4val(evt)
{
	if ((evt.value == "on") || (evt.value == "off" ))
		return
	log.debug "UpdateLevel: $evt"
	int level = fan4.currentValue("level")
	log.debug "level: $level"

    if (level == 0) {
    	arduino.fan4off()
    }
    
     if (level == 30) {
    	arduino.fan4low()
    }
    
     if (level == 60) {
    	arduino.fan4med()
    }
    
     if (level == 100) {
    	arduino.fan4hi()
    }
}

