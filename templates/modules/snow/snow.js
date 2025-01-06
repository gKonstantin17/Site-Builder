(function() {
    // Создание элементов
    var snowBlocks = document.createElement('div');
    snowBlocks.className = 'snow-blocks';
    
    var snow1 = document.createElement('div');
    snow1.className = 'snow1';
    
    var snow2 = document.createElement('div');
    snow2.className = 'snow2';
    
    var content = document.createElement('div');
    content.className = 'content';
    
    // Добавление элементов в snow-blocks
    snowBlocks.appendChild(snow1);
    snowBlocks.appendChild(snow2);
    snowBlocks.appendChild(content);
    
    // Добавление snow-blocks в body или другой контейнер
    document.body.appendChild(snowBlocks);

    // Добавление стилей через .style
    snowBlocks.style.backgroundColor = '#130836';
    snowBlocks.style.position = 'relative';
    snowBlocks.style.height = '100vh';
    snowBlocks.style.width = '100%';
    snowBlocks.style.overflow = 'hidden';
    
    snow1.style.position = 'absolute';
    snow1.style.width = '100%';
    snow1.style.height = '100%';
    snow1.style.backgroundImage = 'url(https://webtort.ru/wp-content/uploads/2019/12/snow1.png)';
    snow1.style.animation = 'snow1 18s linear infinite';

    snow2.style.position = 'absolute';
    snow2.style.width = '100%';
    snow2.style.height = '100%';
    snow2.style.backgroundImage = 'url(https://webtort.ru/wp-content/uploads/2019/12/snow2.png), url(https://webtort.ru/wp-content/uploads/2019/12/snow3.png)';
    snow2.style.animation = 'snow2 10s linear infinite';

    content.style.position = 'relative';
    
    // Добавление анимаций через ключевые кадры
    var styleSheet = document.createElement('style');
    styleSheet.type = 'text/css';
    styleSheet.innerText = `
        @keyframes snow1 {
            from { background-position: 0 -723px; }
            50% { background-position: 40% 0px; }
            to { background-position: 0 723px; }
        }

        @keyframes snow2 {
            0% { background-position: 0 0; }
            100% { background-position: 0 723px; }
        }
    `;
    document.head.appendChild(styleSheet);
})();
