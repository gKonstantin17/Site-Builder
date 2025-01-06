const startupScripts = [ // скрипты в папке scripts и название функции инициализации
    {
      src: 'elementSelector.js',
      functionName: 'initElementSelector'
    },
    {
      src: 'loaderFromJava.js',
      functionName: 'initLoaderFromJava'
    },
    {
      src: 'changeSize.js',
      functionName: 'initChangeSize'
    },
    {
      src: 'createStartSection.js',
      functionName: 'createStartSection'
    }
];
function loadScript(src, callback) {
  const script = document.createElement('script');
  script.src = 'scripts/' + src;
  script.onload = callback; // Вызываем callback, когда скрипт загружен
  document.head.appendChild(script); // Добавляем скрипт в DOM
}
function loadScriptsSequentially(scripts, index = 0) {
  if (index < scripts.length) {
    loadScript(scripts[index].src, function() {
      window[scripts[index].functionName](); // Вызов функции из глобального пространства
      console.log(`${scripts[index].src} загружен, вызвана функция ${scripts[index].functionName}`);
      loadScriptsSequentially(scripts, index + 1); // Загружаем следующий скрипт
    });
  } else {
    console.log('Все скрипты загружены!');
  }
}
loadScriptsSequentially(startupScripts);
