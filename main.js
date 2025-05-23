const { app, BrowserWindow } = require('electron');

function createWindow() {
  const win = new BrowserWindow({
    width: 1500,
    height: 400,
  });

  win.loadFile('index.html');  // Your game's main HTML file
}

app.whenReady().then(createWindow);
