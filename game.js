const canvas = document.getElementById('gameCanvas');
const ctx = canvas.getContext('2d');

const keys = {};

// Player properties
const player = {
  x: 50,
  y: 0,
  width: 50,
  height: 70,
  dx: 0,
  dy: 0,
  speed: 0.3,
  maxSpeed: 2.5,
  friction: 0.85,
  gravity: 0.8,
  jumpStrength: 15,
  jumping: false,
  onGround: false,
  jumpPressedTime: 0,  
};

// Platforms
const platforms = [
  { x: 200, y: 350, width: 100, height: 20 },
  { x: 400, y: 280, width: 100, height: 20 },
  { x: 600, y: 220, width: 120, height: 20 },
  { x: 800, y: 160, width: 100, height: 20 },
  { x: 1000, y: 160, width: 100, height: 20 },
  { x: 1200, y: 160, width: 100, height: 20 },
  { x: 1400, y: 160, width: 90, height: 20 },
];


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

  // Draw player (simple red rectangle)
  // ctx.fillStyle = 'red';
  // ctx.fillRect(player.x, player.y, player.width, player.height);

  // Draw platforms
  ctx.fillStyle = 'white';
  for (let plat of platforms) {
    ctx.fillRect(plat.x, plat.y, plat.width, plat.height);
  }
}

// Game loop
function loop() {
  update();
  draw();
  requestAnimationFrame(loop);
}

// Key event listeners
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

