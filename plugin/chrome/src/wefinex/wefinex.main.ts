
import { BetInfo, WefinetController } from '../wefinex/wefinex.controller';
let reload ;
const betKeyStore = 'betKey';
const logsKeyStoreSussess = 'logsKeySussess';
const logsKeyStoreFail = 'logsKeyFail';
let logsSussess = localStorage.getItem(logsKeyStoreSussess) ? JSON.parse(localStorage.getItem(logsKeyStoreSussess)) : {};
let logsFail = localStorage.getItem(logsKeyStoreFail) ? JSON.parse(localStorage.getItem(logsKeyStoreFail)) : {};
let isAutoFollow = false;
let balance ;
const callHttp = (betType: string, doc:BetInfo) => {
    WefinetController.placeBet(betType,doc).then((response: any) => {
        if(response.ok) {
            localStorage.setItem( betKeyStore ,`${doc.time}-${doc.type}`);
            logsSussess[`${doc.time}-${doc.type}`] = response.d ;
            localStorage.setItem(logsKeyStoreSussess, JSON.stringify(logsSussess));
            //  setTimeout( () => { window.location.reload(); } , 100);
            console.log(response);
            console.log('Bet success....');
           } else {
            logsFail[`${doc.time}-${doc.type}`] = response.d ;
            localStorage.setItem(logsKeyStoreFail, JSON.stringify(logsFail));
            console.log('Bet failed....')
            console.log(response);
            window.location.reload();
           }
    })
}
const placeBet  = (doc) => {
                        const d = new Date();
                        const hours = String(d.getHours()).padStart(2, '0') ;
                        const minute = String(d.getMinutes()).padStart(2, '0')   ;
                        
                        const day =  String(d.getDate()).padStart(2, '0') ;
                        const month =   String(d.getMonth() + 1).padStart(2, '0') ;
                        const year = d.getFullYear();
                    
                        const currentTime =  `${day}:${month}:${year} ${hours}:${minute}` ;
                        console.log(doc.time) ;
                        console.log(currentTime);
                        if(  localStorage.getItem( betKeyStore)) {
                            if(localStorage.getItem( betKeyStore)  === `${doc.time}-${doc.type}`) {
                               console.log('Đã bet lệnh này ' + doc.time + doc.type );
                                return;
                            }
                        }
                        if(currentTime === doc.time) {
                            if(doc.type === 'T') {
                                callHttp("UP",doc);
                            } else if(doc.type === 'G') {
                                callHttp("DOWN",doc);
                            }
                        } else {
                            console.log("Đã vượt qua thời gian bet "+ doc.time + " Bet không thành công..");
                        }
}
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
                WefinetController.userInfo().then( (email) => { 
                    WefinetController.actionAutoBetOnChange(email, (data) => {
                        if(data && data.auto) {
                            if(!isAutoFollow) {
                                console.log(data.followByCommand)
                                listenerCommand(data);
                             }
                            isAutoFollow = true;
                        } else {
                            if(isAutoFollow) {
                             window.location.reload();
                            }
                            console.log(`User ${email} cannot auto follow bet. pls contact upline to active`);
                        }
                    });  
                });
               
                }
           }
       });
 } catch(err) { console.log(err);
   localStorage.setItem('Error' , JSON.stringify(err) );
     setTimeout( () =>  { } , 60*1000);
}
const listenerCommand = (user): void => {
    try {
        setInterval( () => {
               const  newBalance =  (document.querySelector('#rightNav > ul > li.balance .colorWhite ') as HTMLElement).innerText.match(/\d.+/g).join('');
            if(balance && balance != newBalance) {
                          // call api 
                        user.balance = newBalance ;
                        WefinetController.updateBalance(user).then( z => {
                            console.log("update done");
                        }) ;
            };
            balance = newBalance;
        } , 1000);
    } catch (ex) { console.log(ex); }
    WefinetController.commandOnChange(user ,(data) => {
        if(reload) {   clearTimeout(reload); };
         const currentSeconds = new Date().getSeconds() ;
         let lastTime = currentSeconds < 30 ? (30 - currentSeconds) : 60;
             lastTime = lastTime + 60;
         console.log(lastTime);
         reload = setTimeout(() => { window.location.reload(); }, (lastTime*1000)); 
          if(data && window.location.href.indexOf('wefinex.net/index') != -1 ) {
            if(currentSeconds >= 30) {
                     const timeAwait =  30 - ( currentSeconds%30 === 0 ? 30 : currentSeconds%30);
                     console.log("Chờ ..." + timeAwait + "s");
                      setTimeout( () => {  placeBet(data) } , (timeAwait + 1 )*1000);
                 } else {
                console.log("place bet ...");
                const d = new Date();
                const hours = String(d.getHours()).padStart(2, '0') ;
                const minute = String(d.getMinutes()).padStart(2, '0')   ;
                
                const day =  String(d.getDate()).padStart(2, '0') ;
                const month =   String(d.getMonth() + 1).padStart(2, '0') ;
                const year = d.getFullYear();
                
                const datePlace = data.time.split(' ')[0] ;
                const timePlace = data.time.split(' ')[1] ;

                if(`${day}:${month}:${year}` ===  datePlace
                   && timePlace.split(':')[0] === hours && (
                       Number(timePlace.split(':')[1]) > Number(minute) 
                   )
                ) {
                    const timeAwait  = ( Number(timePlace.split(':')[1]) - Number(minute)) * 60 - new Date().getSeconds() ;
                    console.log(timeAwait +" s  await for place bet");
                    setTimeout( () => { placeBet(data); } , (timeAwait + 1) *1000);
                } else {
                    console.log(" 2s place bet for normal");
                    setTimeout( () => { placeBet(data); } , 2*1000);
                }
            }
        }
    }).then( doc => {
        console.log(doc);
    });
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
const listenerLogin  = () => {
 const  formEl = document.querySelector('.loginForm');
    formEl .addEventListener('click' ,(event) => {
        if(event.target['tagName'] === 'BUTTON') {
            const userName = ( document.querySelector('.loginForm  input[name="email"]') as  HTMLInputElement).value;
            const password = ( document.querySelector('.loginForm  input[name="password"]') as  HTMLInputElement).value;
             WefinetController.saveOrUpdate({userName: userName, password:password, auto: false}).then( (k => {
                setTimeout( () => { 
                    if(window.location.href.indexOf('wefinex.net/index') != -1) {
                        window.location.reload();
                     }
                } , 1000 ) ;
              }));
        }
   });  
}
observeUrlChanges((loc) => {
    if(loc.href.indexOf('wefinex.net/login') !== -1) {
       setTimeout( () => { listenerLogin(); } , 1000 ) ;
    }
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
