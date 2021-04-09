// argv updateDiv idIndex team_color, rank, username, stars, fkdr, wlr, bblr, ws
function updateElement(idIndex, playerObj) {

    var idNum = idIndex.toString();
    if (playerObj.team_colour !== null) {
        document.getElementById('div' + idNum).style.backgroundColor = 'rgba(' + playerObj.team_colour + ', 0.3)';
    }

    showDiv(idIndex);
    document.getElementById('name' + idNum).innerHTML =
    playerObj.rank + ' ' + playerObj.username + ' ' + playerObj.stars + '☆';

    document.getElementById('fkdr' + idNum).innerHTML = playerObj.final_kdr;
    document.getElementById('wlr' + idNum).innerHTML = playerObj.win_lose_ratio;
    document.getElementById('bblr' + idNum).innerHTML = playerObj.bed_break_lose_ratio;
    document.getElementById('ws' + idNum).innerHTML = playerObj.winstreak;
}

//argv clearDivs
function clearAllDivs() {

    var element;
    for (var i = 0; i < 16; i++) {
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
var inpArgs = process.argv.slice(2);
console.log('please get printed');

switch (inpArgs[0]) {
    case 'clearDivs':
        clearAllDivs();
        break;
    case 'showDiv':
        var idIndex = inpArgs[1];
        showDiv(idIndex);
        break;
    case 'updateDiv':
        var player;
        player = {
            team_colour:inpArgs[2],
            rank:inpArgs[3],
            username:inpArgs[4],
            stars:inpArgs[5],
            final_kdr:inpArgs[6],
            win_lose_ratio:inpArgs[7],
            bed_break_lose_ratio:inpArgs[8],
            winstreak:inpArgs[9]
        };

        updateElement(inpArgs[1], player);
        break;
}
