import  { WefinetController } from './wefinex.controller';
let reload ;
let lastResultSaved =  localStorage.getItem('LASTSAVED') || "" ;
try {
      const actionType =  new URL(window.location.href).searchParams.get("actionType") || "MAIN";
      chrome.runtime.sendMessage({action: "WEFINEX", domain: window.location.hostname.replace(/(https?:\/\/)?(www.)?/i, ''), actionType: actionType }, (response) => {
        const lastError = chrome.runtime.lastError;
                if (lastError) {
                    console.log(lastError.message);
                     window.location.reload();
                    return;
                }
           if((response.data || []).filter(z => z.url.indexOf(window.location.hostname) !== -1).length > 1 )  {
               alert('Please open only one window for https://wefinex.net');
               window.location.href = 'http://google.com/';
           } else {
               if(window.location.href.indexOf('wefinex.net/index') != -1 ) {      
                    startServer();
                }
           }
       });
 } catch(err) { console.log(err);
   localStorage.setItem('Error' , JSON.stringify(err) );
     setTimeout( () =>  { } , 60*1000);
}
const saveResult = (data) => {
    if(data.status == 1 || new Date().getSeconds() >= 30) { console.log('< 30s'); return ; }
    if(lastResultSaved+"" !== data.settledDateTime+"") {
        WefinetController.updateResult(data).then( z=> {
            lastResultSaved = data.settledDateTime+"";
            localStorage.setItem('LASTSAVED',lastResultSaved);
            console.log("saved ----> "  + (new Date(Number(lastResultSaved)).getSeconds()) + " " + lastResultSaved + " "  + data.type + " " +  data.status) ;
        });
    } else {
        console.log("this is result is saved");
    }
}
const startServer = (): void => {
    const currentSeconds = new Date().getSeconds() ;
    let lastTime = currentSeconds < 30 ? (30 - currentSeconds) : 60;
        lastTime = lastTime + 60;
        console.log(lastTime);
    reload = setTimeout(() => { window.location.reload(); }, (lastTime*1000)); 

  
    const collectData = () => {
        const minutes = new Date().getSeconds();
        const x =  60 ; //  30s hoặc 60s
        const time = minutes%x ;
        const start = (x - ((time===0)? x :  time) );
        setTimeout( () => {
            console.log("call api");
            WefinetController.chartData().then( (data: any[]) => {
                saveResult(data[0]);
            })
            collectData();
        }, (start + 1 )* 1000);
    }
         collectData();
    console.log("Server start.........")  ;  
    WefinetController.chartData().then( (data: any[]) => {
     
        saveResult(data[0]);
    })
}
 
  
// handle change url
// listener local change
let oldUrl ;
const locationChangeEventType = "MY_APP-location-change";
// called on creation and every url change
 const observeUrlChanges = (cb) => {
    assertLocationChangeObserver();
    window.addEventListener(locationChangeEventType, () => cb(window.location));
    cb(window.location);
}
 const assertLocationChangeObserver = () => {
    let state = window;
    if (state['MY_APP_locationWatchSetup']) {
        return;
    }
    state['MY_APP_locationWatchSetup'] = true;
    let lastHref = location.href;
        document.querySelector("body").addEventListener("click", () => {
            requestAnimationFrame(() => {
                const currentHref = location.href;
                if (currentHref !== lastHref) {
                    lastHref = currentHref;
                    window.dispatchEvent(new Event(locationChangeEventType));
                }
            });
        });
}
observeUrlChanges((loc) => {
    if(!oldUrl) {
        // Lần đầu tiên vào trang
       
    } else {
        // chuyển hướng 
        if(loc.href.indexOf('wefinex.net/index') !== -1) {
            window.location.reload();
        }
    }
       oldUrl = loc.href;
});
