const WebApp = window.Telegram.WebApp;
const PostGameResultUri = 'https://5bukv.fun/game/result'
const PostGameResultServiceUri = 'https://5bukv.fun/service/gameresult'
WebApp.expand()

async function sendResult(game_ind, status){
    if (WebApp.initData){
              let params = (new URL(document.location)).searchParams;
              const botId = parseInt(params.get('bot'))
              const rawInitData = WebApp.initData
              const userId = WebApp.initDataUnsafe.user.id
               data = {
                "init_data": rawInitData,
                "bot_id": botId,
                "game_ind": game_ind,
                "status": status
               }
               dataToSave = {
                               "bot_id": botId,
                               "game_ind": game_ind,
                               "status": status,
                               "chat_id": userId
                              }
                              alert(botId+game_ind+status+userId);
                gameFinishedResponse = await fetch(PostGameResultUri, {
                                                    method: 'POST',
                                                    headers: {
                                                        'Accept': 'application/json',
                                                        'Content-Type': 'application/json'
                                                    },
                                                    body: JSON.stringify(dataToSave) });

                response = await fetch(PostGameResultServiceUri, {
                  method: 'POST',
                  headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
                  },
                  body: JSON.stringify(data) });
                  statusRequest = await response.status
                  result = await response.json()
//                  console.log("STATUS: "+statusRequest)
//                  console.log("Result:"); console.log(result)
//                  console.log("MSG: "+result.message)

    }

}
