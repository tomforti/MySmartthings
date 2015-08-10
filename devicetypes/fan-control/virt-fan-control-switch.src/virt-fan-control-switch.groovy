metadata {
	definition (name: "Virt Fan Control Switch", namespace: "Fan Control", author: "tom") {
		capability "Switch"
		capability "Switch Level"
        capability "Refresh"

        command "lowSpeed"
        command "medSpeed"
        command "highSpeed"
        command "push"

		attribute "currentSpeed", "string"

	}

	tiles {
        standardTile("switch", "device.switch", width: 2, height: 2, canChangeIcon: true) {
			state "off", label: 'Fan Off', action: "push", icon:"st.Lighting.light24", backgroundColor: "#ffffff", nextState: "on"
			state "on", label: 'Fan Off', action: "push", icon:"st.Lighting.light24", backgroundColor: "#79b821"    

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
def push() {
	sendEvent(name: "switch", value: "on", isStateChange: true, display: false)
	sendEvent(name: "switch", value: "off", isStateChange: true, display: false)
	sendEvent(name: "momentary", value: "pushed", isStateChange: true)
    setLevel(0)
	log.info "Fan Off"
    sendEvent(name: "currentSpeed", value: "Off" as String)
}

def on() {
	push()
}

def off() {
	push()
}


def setLevel(val){
    log.info "setLevel $val"

    	sendEvent(name:"level",value:val)
    	sendEvent(name:"switch.setLevel",value:val) // had to add this to work if apps subscribed to
        											// setLevel event. "Dim With Me" was one.
        if (val == "0"){ 
       		sendEvent(name: "currentSpeed", value: "OFF" as String)
   		}
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