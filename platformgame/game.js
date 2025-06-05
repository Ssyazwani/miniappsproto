const canvas = document.getElementById('gameCanvas');
const ctx = canvas.getContext('2d');

const keys = {};


const player = {
  x: 50,
  y: 0,
  width: 70,
  height: 70,
  dx: 0,
  dy: 0,
  speed: 0.25,
  maxSpeed: 2.5,
  friction: 0.85,
  gravity: 0.8,
  jumpStrength: 15,
  jumping: false,
  onGround: false,
  jumpPressedTime: 0,  
};


// const platforms = [
//   { x: 50, y: 380, width: 100, height: 20 },
//   { x: 150, y: 320, width: 100, height: 20 },
//   { x: 270, y: 260, width: 100, height: 20 },
//   { x: 400, y: 200, width: 100, height: 20 },
//   { x: 550, y: 140, width: 100, height: 20 },
// ];


const platforms = [];
for (let i = 0; i < 5; i++) {
  platforms.push({
    x: 50 + i * 120, 
    y: 380 - i * 60,
    width: 100,
    height: 20
  });
}




function update() {
 
  if (keys['ArrowRight'] || keys['KeyD']) {
    player.dx += player.speed;
  } else if (keys['ArrowLeft'] || keys['KeyA']) {
    player.dx -= player.speed;
  } else {
    player.dx *= player.friction;
  }

 
  if (player.dx > player.maxSpeed) player.dx = player.maxSpeed;
  if (player.dx < -player.maxSpeed) player.dx = -player.maxSpeed;

  player.x += player.dx;

 
  if (player.x < 0) {
    player.x = 0;
    player.dx = 0;
  }
  if (player.x + player.width > canvas.width) {
    player.x = canvas.width - player.width;
    player.dx = 0;
  }


  if (player.jumping) {
   
    if (!keys['Space'] && player.dy < 0) {
      player.dy += player.gravity * 1.5; 
      player.jumping = false;
    }
  }

  player.dy += player.gravity;
  player.y += player.dy;

  player.onGround = false;


  for (let plat of platforms) {
    if (
      player.x < plat.x + plat.width &&
      player.x + player.width > plat.x &&
      player.y + player.height > plat.y &&
      player.y + player.height < plat.y + plat.height &&
      player.dy >= 0
    ) {
   
      player.y = plat.y - player.height;
      player.dy = 0;
      player.onGround = true;
      player.jumping = false;
    }
  }


  if (player.y + player.height > canvas.height) {
    player.y = canvas.height - player.height;
    player.dy = 0;
    player.onGround = true;
    player.jumping = false;
  }

    if (!reachedGoal && isOnPlatform(player, highestPlatform)) {
    reachedGoal = true;
  }
}

const playerImage = new Image();
playerImage.src = 'tuxSam.png';

function draw() {
  ctx.clearRect(0, 0, canvas.width, canvas.height);

  if (playerImage.complete && playerImage.naturalWidth !== 0) {
    ctx.drawImage(playerImage, player.x, player.y, player.width, player.height);
  } else {
   
    ctx.fillStyle = 'lime';
    ctx.fillRect(player.x, player.y, player.width, player.height);
  }

 

 
  ctx.fillStyle = 'white';
  for (let plat of platforms) {
    ctx.fillRect(plat.x, plat.y, plat.width, plat.height);
  }

 if (reachedGoal) {
  ctx.fillStyle = "deepskyblue"; 
  ctx.font = "bold 36px 'Times New Roman', cursive"; 
  ctx.textAlign = "center"; 
  ctx.fillText("Yay you did it!!!!!", canvas.width / 2, 80);
}

}

let reachedGoal = false;
function isOnPlatform(player, platform) {
  return (
    player.x + player.width > platform.x &&
    player.x < platform.x + platform.width &&
    player.y + player.height >= platform.y &&
    player.y + player.height <= platform.y + platform.height + 5
  );
}
const highestPlatform = platforms.reduce((top, p) =>
  p.y < top.y ? p : top
);



function loop() {
  update();
  draw();
  requestAnimationFrame(loop);
}

document.addEventListener('keydown', (e) => {
  keys[e.code] = true;

  if ((e.code === 'Space' || e.code === 'KeyW' || e.code === 'ArrowUp') && player.onGround) {
    player.dy = -player.jumpStrength;
    player.jumping = true;
    player.onGround = false;
  }
});

document.addEventListener('keyup', (e) => {
  keys[e.code] = false;
});

loop();

loop();

