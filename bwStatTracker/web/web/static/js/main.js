// argv updateDiv idIndex team_color, rank, username, stars, fkdr, wlr, bblr, ws
function updateElement() {



    console.log(fullUrl);
    console.log(jsonUrl);

    // $.getJSON(jsonUrl, function(from_to_conversions){
    //
    //     let jsonData = from_to_conversions;
    //     let counter = 0;
    //     let counterStr;
    //     let playerObj;
    //     console.log(jsonData.length);
    //     if(jsonData.length > 0){
    //
    //         for(playerObj of jsonData){
    //             console.log(playerObj);
    //             counter++;
    //             counterStr = counter.toString();
    //
    //             if (playerObj.team_colour !== null) {
    //                 document.getElementById('div' + counterStr).style.backgroundColor = 'rgba(' + playerObj.team_colour + ', 0.3)';
    //             }
    //
    //             document.getElementById('name' + counterStr).innerHTML =
    //             playerObj.rank + ' ' + playerObj.username + ' ' + playerObj.stars + '☆';
    //
    //             document.getElementById('fkdr' + counterStr).innerHTML = playerObj.final_kdr;
    //             document.getElementById('wlr' + counterStr).innerHTML = playerObj.win_lose_ratio;
    //             document.getElementById('bblr' + counterStr).innerHTML = playerObj.bed_break_lose_ratio;
    //             document.getElementById('ws' + counterStr).innerHTML = playerObj.winstreak;
    //         }
    //     }else{
    //         clearAllDivs()
    //     }
    //
    //
    // });

    $.ajax({
        dataType: "json",
        url: jsonUrl,
        cache: false,
        success: function(data){
            let jsonData = data;
            let counter = 0;
            let counterStr;
            let playerObj;
            console.log(data);



            jsonData = sortPlayerArr(jsonData, 'final_kdr');
            clearAllDivs();
            for(playerObj of jsonData){
                counter++;
                counterStr = counter.toString();

                document.getElementById('div' + counterStr).style.display = 'block';
                if (playerObj.team_colour !== null) {
                    document.getElementById('div' + counterStr).style.backgroundColor = 'rgba(' + playerObj.team_colour + ', 0.3)';
                }else{
                    document.getElementById('div' + counterStr).style.backgroundColor = 'rgba(white, 0.2)';
                }

                let rank = refactorRank(playerObj.rank);

                document.getElementById('name' + counterStr).innerHTML =
                rank + ' ' + playerObj.username + ' ' + playerObj.stars + '☆';

                let fkdr = playerObj.final_kdr;
                document.getElementById('fkdr' + counterStr).innerHTML = fkdr;
                for (const [key, value] of Object.entries(fkdrStatColours)){
                    if(fkdr < value[0]){
                        document.getElementById('fkdr' + counterStr).style.color = value[1];
                        break;
                    }
                }

                let wlr = playerObj.win_lose_ratio;
                document.getElementById('wlr' + counterStr).innerHTML = wlr;
                for (const [key, value] of Object.entries(wlrStatColours)){
                    if(wlr < value[0]){
                        document.getElementById('wlr' + counterStr).style.color = value[1];
                        break;
                    }
                }

                let bblr = playerObj.bed_break_lose_ratio;
                document.getElementById('bblr' + counterStr).innerHTML = bblr;
                for (const [key, value] of Object.entries(bblrStatColours)){
                    if(bblr < value[0]){
                        document.getElementById('bblr' + counterStr).style.color = value[1];
                        break;
                    }
                }

                let ws = playerObj.winstreak;
                document.getElementById('ws' + counterStr).innerHTML = ws;
                for (const [key, value] of Object.entries(wsStatColours)){
                    if(ws < value[0]){
                        document.getElementById('ws' + counterStr).style.color = value[1];
                        break;
                    }
                }
            }

        },
        error: function(data){
            console.log("clearing")
            clearAllDivs()
        }
    })
}

const fullUrl = document.getElementById("mainScript").src;
const jsonUrl = fullUrl.replace("main.js", "playerStats.json");

const fkdrStatColours = {

    safe:[1, "green"],
    low_euclid:[2, "yellow"],
    high_euclid:[3, "orange"],
    low_keter:[5, "darkred"],
    high_keter:[8, "red"],
    low_apollyon:[15, "black"],
    high_apollyon:[10000, "white"]

};

const wlrStatColours = {

    safe:[0.4, "green"],
    low_euclid:[0.8, "yellow"],
    high_euclid:[1.5, "orange"],
    low_keter:[3, "darkred"],
    high_keter:[5, "red"],
    low_apollyon:[10, "black"],
    high_apollyon:[10000, "white"]
}

const bblrStatColours = {

    safe:[0.8, "green"],
    low_euclid:[1.6, "yellow"],
    high_euclid:[2.5, "orange"],
    low_keter:[4, "darkred"],
    high_keter:[7, "red"],
    low_apollyon:[12, "black"],
    high_apollyon:[10000, "white"]
}

const wsStatColours = {

    safe:[3, "green"],
    low_euclid:[7, "yellow"],
    high_euclid:[12, "orange"],
    low_keter:[25, "darkred"],
    high_keter:[60, "red"],
    low_apollyon:[150, "black"],
    high_apollyon:[10000, "white"]
}
// function readTextFile(file, callback) {
//     var rawFile = new XMLHttpRequest();
//     // rawFile.overrideMimeType("application/json");
//     rawFile.open("GET", 'playerStats.json');
//     rawFile.onreadystatechange = function() {
//         rawFile.onload = function() {
//             var xreq = JSON.parse(rawFile.responseText);
//         }
//     };
//     rawFile.send(null);
// }

function refactorRank(rank){
    if(rank === null){
        rank = '';
    }else if(rank === 'VIP'){
        rank = '[VIP]';
    }else if(rank === 'VIP_PLUS'){
        rank = '[VIP+]';
    }else if(rank === 'MVP'){
        rank = '[MVP]';
    }else if(rank === 'MVP_PLUS'){
        rank = '[MVP+]';
    }else if(rank === 'MVP_PLUS_PLUS'){
        rank = '[MVP++]';
    }

    return rank;
}

function sortPlayerArr(data, type){
    // sort by fkdr -> wlr -> ws
    if(type === 'final_kdr'){
        data.sort( (a,b) => {
            if (a.final_kdr < b.final_kdr) return 1;
            else if (a.final_kdr === b.final_kdr){
                if (a.win_lose_ratio < b.win_lose_ratio) return 1;
                else if (a.win_lose_ratio === b.win_lose_ratio){
                    if (a.winstreak < b.winstreak){
                        return 1;
                    }else{
                        return -1;
                    }
                }
                else return -1
            }
            else return -1;
        });
    }else if(type === 'win_lose_ratio'){
        // sort by wlr -> fkdr -> winstreak
        data.sort( (a,b) => {
            if (a.win_lose_ratio < b.win_lose_ratio) return 1;
            else if (a.win_lose_ratio === b.win_lose_ratio){
                if (a.final_kdr < b.final_kdr) return 1;
                else if (a.final_kdr === b.final_kdr){
                    if (a.winstreak < b.winstreak){
                        return 1;
                    }else{
                        return -1;
                    }
                }
                else return -1
            }
            else return -1;
        });
    }else if (type === 'winstreak'){
        // sort by ws -> fkdr -> wlr
        data.sort( (a,b) => {
            if (a.winstreak < b.winstreak) return 1;
            else if  (a.winstreak === b.winstreak){
                if (a.final_kdr < b.final_kdr) return 1;
                else if  (a.final_kdr === b.final_kdr){
                    if  (a.win_lose_ratio < b.win_lose_ratio) return 1;
                    else return -1;
                }
                else return -1;
            }
            else return -1;
        });
    }

    return data;
}

//argv clearDivs
function clearAllDivs() {

    let element;
    for (let i = 1; i <= 16; i++) {
        element = document.getElementById('div' + i.toString());
        element.style.display = 'none';

    }
}

// argv showDiv index
function showDiv(idIndex){

    var idNum = idIndex.toString();
    var element = document.getElementById('div' + idNum);
    if (element.style.display === 'none') {
        element.style.display = 'block';
    }
}

function testFunc(){
    alert('for once something works');
}

//process argv
// var inpArgs = process.argv.slice(2);
// console.log('please get printed');
//
// switch (inpArgs[0]) {
//     case 'clearDivs':
//         clearAllDivs();
//         break;
//     case 'showDiv':
//         var idIndex = inpArgs[1];
//         showDiv(idIndex);
//         break;
//     case 'updateDiv':
//         var player;
//         player = {
//             team_colour:inpArgs[2],
//             rank:inpArgs[3],
//             username:inpArgs[4],
//             stars:inpArgs[5],
//             final_kdr:inpArgs[6],
//             win_lose_ratio:inpArgs[7],
//             bed_break_lose_ratio:inpArgs[8],
//             winstreak:inpArgs[9]
//         };
//
//         updateElement(inpArgs[1], player);
//         break;
// }
