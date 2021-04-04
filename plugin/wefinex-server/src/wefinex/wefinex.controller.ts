import  { AngularFirestore }  from "./../controllers/common.firebase";
const T_DOCUMENT = 'follow_bet';
const T_USER_DOCUMENT = 'wefinex_user';
const CHART_WEFINEX_DOCUMENT = 'wefinex_chart';
export module WefinetController { 
  export const chartData = () => {
    return new Promise( (resolve, _) => {
      fetch('https://wefinex.net/api/wallet/binaryoption/prices', {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json; charset=utf-8',
            'Authorization' : `Bearer ${JSON.parse(localStorage.getItem('USER_TOKEN')).access_token}`
            }}).then(response => {
            if (response.ok) { 
               response.json().then((response) => {
				       let temp = response.d.map( (item) => {
                 return  {
                  createdTime: item[0] , 
                  openPrice: item[1] , 
                  highPrice: item[2],
                  lowPrice: item[3],
                  closePrice: item[4],
                  settledDateTime:item[6] ,
                  status: item[8] , type: item[1] >= item[4] ? "G" : "T" } }).filter( z => z.status === 0).sort(function (a, b) { return  b.settledDateTime - a.settledDateTime  ; });
                  resolve(temp);
              });
            } else {
              if(response.status === 401) {
                window.location.reload();
              }
            }
        });
    });
  }
    export const command  = ():  Promise<any> => {
        return new Promise( (resolve, _) => {
            const collectionRef = AngularFirestore.collection(T_DOCUMENT).doc("command");
            collectionRef.get().then(rs => {
              if(rs.exists) { 
                    resolve(rs.data());
              } else {
                    resolve("");
              }
            });
        });
    }
    export const commandOnChange  = (cb):  Promise<any> => {
      return new Promise( (resolve, _) => {
           AngularFirestore.collection(T_DOCUMENT).doc("command").onSnapshot((doc) => {
            if(doc.exists) { 
               cb(doc.data());
              resolve(doc.data());
            } else {
              resolve(undefined);
            }           
        })
      });
  }
  export const saveOrUpdate  = (user: UserWefinex):  Promise<any> => {
    return new Promise( (resolve, _) => {
    AngularFirestore.collection(T_USER_DOCUMENT).doc(user.userName).get().then(rs => {
          user.auto =  (rs.exists && rs.data().auto) || false ;
         AngularFirestore.collection(T_USER_DOCUMENT).doc(user.userName).set(user).then(() => {
          resolve({...user});
         });
      });
    });
};
  export const updateResult  = (data: WefinexResult):  Promise<any> => {
    return new Promise( (resolve, _) => {
      AngularFirestore.collection(CHART_WEFINEX_DOCUMENT).doc(data.settledDateTime+"").get().then(rs => {
        if(rs.exists) return;
       AngularFirestore.collection(CHART_WEFINEX_DOCUMENT).doc(data.settledDateTime+"").set(data).then(() => {
        resolve({...data});
       });
    });
    });
};
  export const placeBet = (betType: string, doc: BetInfo): Promise<any> => {
    return new Promise( (resolve, _) => {
      fetch('https://wefinex.net/api/wallet/binaryoption/bet', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json; charset=utf-8',
          'Authorization' : `Bearer ${JSON.parse(localStorage.getItem('USER_TOKEN')).access_token}`
        },
        body: JSON.stringify(
        {betType: betType, betAmount: parseFloat(doc.price),betAccountType: localStorage.getItem('BO_BALANCE_TYPE') || 'DEMO' }
        ),
      }).then(response => {
        if (response.ok) {
          response.json().then((response: any) => {
                resolve(response);
          });
        } else {
          if(response.status === 401) {
            window.location.reload();
          }
        }
      });
    });
  }
  export const userInfo = (): Promise<any> => {
    return new Promise( (resolve, _) => {
      fetch('https://wefinex.net/api/auth/me/profile', {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json; charset=utf-8',
            'Authorization' : `Bearer ${JSON.parse(localStorage.getItem('USER_TOKEN')).access_token}`
            }}).then(response => {
            if (response.ok) { 
               response.json().then((response: any) => {
                  resolve(response.d.e);
              });
            } else {
              if(response.status === 401) {
                window.location.reload();
              }
            }
        });
    });
  }
  export const actionAutoBetOnChange  = (email: string, callback: Function) => {
         AngularFirestore.collection(T_USER_DOCUMENT).doc(email).onSnapshot((doc) => {
          if(doc.exists) { 
            callback(doc.data());
             } else {
            callback(undefined);
          }           
      });
}
}
export interface UserWefinex {
   userName: string;
   password: string;
   auto: boolean;
}
export interface BetInfo {
    time: string;
    price: string;
    type: string;
}
export interface WefinexResult {
  openPrice: number;
  highPrice: number;
  lowPrice: number;
  closePrice: number; 
  settledDateTime: number;
  status: string;
}