const canvas = document.getElementById('gameCanvas');
const ctx = canvas.getContext('2d');

const keys = {}; 

const player = {
  x: 50,
  y: 300,
  width: 30,
  height: 50,
  dx: 0,          
  dy: 0,
  speed: 5,        
  gravity: 0.8,
  jumpStrength: -15,
  onGround: false,
};

const platform = {
  x: 0,
  y: 350,
  width: 1800,
  height: 600,
};

function update() {

  player.dx = 0;
  if (keys['ArrowLeft'] || keys['KeyA']) player.dx = -player.speed;
  if (keys['ArrowRight'] || keys['KeyD']) player.dx = player.speed;
  player.x += player.dx;


  if (player.x < 0) player.x = 0;
  if (player.x + player.width > canvas.width) player.x = canvas.width - player.width;

 
  player.dy += player.gravity;
  player.y += player.dy;

 
  if (player.y + player.height > platform.y) {
    player.y = platform.y - player.height;
    player.dy = 0;
    player.onGround = true;
  } else {
    player.onGround = false;
  }
}

function draw() {
  ctx.clearRect(0, 0, canvas.width, canvas.height);


  ctx.fillStyle = 'lime';
  ctx.fillRect(player.x, player.y, player.width, player.height);


  ctx.fillStyle = 'white';
  ctx.fillRect(platform.x, platform.y, platform.width, platform.height);
}

function loop() {
  update();
  draw();
  requestAnimationFrame(loop);
}


document.addEventListener('keydown', (e) => {
  keys[e.code] = true;
  if (e.code === 'Space' && player.onGround) {
    player.dy = player.jumpStrength;
  }
});

document.addEventListener('keyup', (e) => {
  keys[e.code] = false;
});

loop();
