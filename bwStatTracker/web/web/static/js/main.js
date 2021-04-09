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
            console.log(jsonData.length);
            if(jsonData.length > 0){

                for(playerObj of jsonData){
                    console.log(playerObj);
                    counter++;
                    counterStr = counter.toString();

                    if (playerObj.team_colour !== null) {
                        document.getElementById('div' + counterStr).style.backgroundColor = 'rgba(' + playerObj.team_colour + ', 0.3)';
                    }

                    document.getElementById('name' + counterStr).innerHTML =
                    playerObj.rank + ' ' + playerObj.username + ' ' + playerObj.stars + '☆';

                    document.getElementById('fkdr' + counterStr).innerHTML = playerObj.final_kdr;
                    document.getElementById('wlr' + counterStr).innerHTML = playerObj.win_lose_ratio;
                    document.getElementById('bblr' + counterStr).innerHTML = playerObj.bed_break_lose_ratio;
                    document.getElementById('ws' + counterStr).innerHTML = playerObj.winstreak;
                }
            }else{
                clearAllDivs()
            }
        }
    })
}

const fullUrl = document.getElementById("mainScript").src;
const jsonUrl = fullUrl.replace("main.js", "playerStats.json");

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



//argv clearDivs
function clearAllDivs() {

    var element;
    for (var i = 1; i <= 16; i++) {
        element = document.getElementById('div' + i.toString());
        if (element.style.display !== 'none') {
            element.style.display = 'none';
        }
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
