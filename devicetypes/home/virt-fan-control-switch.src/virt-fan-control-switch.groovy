metadata {
	definition (name: "Virt Fan Control Switch", namespace: "home", author: "tom") {
		capability "Switch"
		capability "Switch Level"
        capability "Refresh"

        command "lowSpeed"
        command "medSpeed"
        command "highSpeed"

		attribute "currentSpeed", "string"

	}

	tiles {
		standardTile("switch", "device.switch", width: 2, height: 2, canChangeIcon: true) {
			state "on", label:'${name}', action:"switch.off", icon:"st.Lighting.light24", backgroundColor:"#79b821"
			state "off", label:'${name}', action:"switch.on", icon:"st.Lighting.light24", backgroundColor:"#ffffff"

		}
		controlTile("levelSliderControl", "device.level", "slider", height: 1, width: 3, inactiveLabel: false) {
			state "level", action:"switch level.setLevel"
		}
        valueTile("currentSpeed", "device.currentSpeed", canChangeIcon: false, inactiveLabel: false, decoration: "flat") {
            state ("default", label:'${currentValue}')
        }

//Speed control row
        standardTile("lowSpeed", "device.level", inactiveLabel: false, decoration: "flat") {
            state "lowSpeed", label:'LOW', action:"lowSpeed", icon:"st.Home.home30"
        }
        standardTile("medSpeed", "device.level", inactiveLabel: false, decoration: "flat") {
            state "medSpeed", label:'MED', action:"medSpeed", icon:"st.Home.home30"
        }
        standardTile("highSpeed", "device.level", inactiveLabel: false, decoration: "flat") {
            state "highSpeed", label:'HIGH', action:"highSpeed", icon:"st.Home.home30"
        }
        standardTile("refresh", "device.switch", inactiveLabel: false, decoration: "flat") {
			state "default", label:"", action:"refresh.refresh", icon:"st.secondary.refresh"
		}
		main(["switch"])
		details(["switch", "refresh", "currentSpeed", "lowSpeed", "medSpeed", "highSpeed"])
	}
}

def parse(String description) {
}
	
def on() {
	log.info "on"
    sendEvent(name:"switch",value:"on")
	
}

def off() {
	log.info "off"
    sendEvent(name:"switch",value:"off")
    sendEvent(name: "currentSpeed", value: "OFF" as String)
}

def setLevel(val){
    log.info "setLevel $val"

    // make sure we don't drive switches past allowed values (command will hang device waiting for it to
    // execute. Never commes back)
    if (val < 0){
    	val = 0
    }
    
    if( val > 100){
    	val = 100
    }
    
    if (val == 0){ // I liked that 0 = off
    	sendEvent(name:"level",value:val)
    	off()
        sendEvent(name: "currentSpeed", value: "OFF" as String)
    }
    else
    {
    	on()
    	sendEvent(name:"level",value:val)
    	sendEvent(name:"switch.setLevel",value:val) // had to add this to work if apps subscribed to
        											// setLevel event. "Dim With Me" was one.
         if (val == "30") {
        	sendEvent(name: "currentSpeed", value: "LOW" as String)
        }
        if (val == "60") {
        	sendEvent(name: "currentSpeed", value: "MED" as String)
        }
        if (val == "100") {
        	sendEvent(name: "currentSpeed", value: "HIGH" as String)
        }                                                  
    }
}

def setLevel(val, dur){ //not called, but leave here for hue and other controllables
  log.info "setLevel $val, $dur"
  sendEvent(name:"setLevel",value:val)
}

def lowSpeed() {
	setLevel(30)
	log.info "Fan low"
    sendEvent(name: "currentSpeed", value: "LOW" as String)
        
}

def medSpeed() {
	setLevel(60)
	log.info "Fan Med"
    sendEvent(name: "currentSpeed", value: "MED" as String)
}

def highSpeed() {
	setLevel(100)
	log.info "Fan High"
    sendEvent(name: "currentSpeed", value: "HIGH" as String)
}