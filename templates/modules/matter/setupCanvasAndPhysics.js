function setupCanvasAndPhysics(imageLinks,number) {
  // Создаем контейнер и элементы
  var canvasContainer = document.createElement('div');
  canvasContainer.className = 'canvas-container';

  var canvas = document.createElement('canvas');
  canvas.id = 'myCanvas';
  canvas.className = 'canvas-box';

  // Добавляем канвас в контейнер
  canvasContainer.appendChild(canvas);

  // Добавляем изображения в контейнер
  imageLinks.forEach(function(src) {
    var img = document.createElement('img');
    img.className = 'img-class';
    img.src = src;
    img.style.display = 'none';
    canvasContainer.appendChild(img);
  });

  // Добавляем контейнер в body
  document.body.appendChild(canvasContainer);

  // Получаем доступ к Matter.js
  const { Engine, Render, Runner, Bodies, World, Mouse, MouseConstraint } = Matter;

  // Размеры экрана
  const canvasWidth = window.innerWidth;
  const canvasHeight = window.innerHeight;

  // Получаем доступ к канвасу и устанавливаем его размеры
  canvas.width = canvasWidth;
  canvas.height = canvasHeight;

  // Создаем движок Matter.js
  const engine = Engine.create();
  const world = engine.world;

  // Настроим рендер для отрисовки на канвасе
  const render = Render.create({
    element: document.body,
    engine: engine,
    canvas: canvas, // Используем созданный канвас
    options: {
      width: canvasWidth,
      height: canvasHeight,
      wireframes: false, // Без каркасного режима
      background: '#ffffff'
    }
  });

  // Запуск рендера
  Render.run(render);

  // Создаем бегун для обновления физики
  const runner = Runner.create();
  Runner.run(runner, engine);

  // Функция для создания падающих объектов с изображениями
  function createFallingObjects() {
    const images = document.querySelectorAll('.img-class'); // Получаем все изображения с классом img-class

    images.forEach(image => {
      image.onload = () => {
        for (let i = 0; i < Number(number); i++) {
          const width = Math.random() * 50 + 70; // Случайная ширина изображения
          const height = (width * image.height) / image.width; // Сохраняем пропорции изображения
          const wallWidth = 50; // Ширина стен
          const x = Math.random() * (canvasWidth - width - 2 * wallWidth) + wallWidth; // Область появления без стен
          const y = -100; // Начальная позиция за пределами экрана

          // Создаем тело с изображением
          const imageBody = Bodies.rectangle(x, y, width, height, {
            restitution: 0.7, // Отскок
            frictionAir: 0.01, // Меньшее трение для более плавного движения
            render: {
              sprite: {
                texture: image.src,
                xScale: width / image.width, // Масштабируем изображение по ширине
                yScale: height / image.height // Масштабируем изображение по высоте
              }
            }
          });

          // Добавляем объект в мир
          World.add(world, imageBody);
        }
      };

      // Обеспечим, что изображение загружается
      if (image.complete) {
        image.onload();
      }
    });
  }

  // Создаем ограничение мира (границы)
  const ground = Bodies.rectangle(canvasWidth / 2, canvasHeight, canvasWidth, 10, { isStatic: true });
  const leftWall = Bodies.rectangle(0, canvasHeight / 2, 10, canvasHeight, { isStatic: true }); // Левая граница
  const rightWall = Bodies.rectangle(canvasWidth, canvasHeight / 2, 10, canvasHeight, { isStatic: true }); // Правая граница

  // Добавляем стены в мир
  World.add(world, [ground, leftWall, rightWall]);

  // Настройка мыши для управления объектами
  const mouse = Mouse.create(render.canvas);
  const mouseConstraint = MouseConstraint.create(engine, {
    mouse: mouse,
    constraint: {
      stiffness: 0.05,  // Уменьшаем жесткость, чтобы уменьшить ускорение
      damping: 0.5,     // Включаем демпфирование для замедления движения
      render: {
        visible: false
      }
    }
  });
  World.add(world, mouseConstraint);

  // Обработчик событий для изменения размера экрана
  window.addEventListener('resize', () => {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
    engine.world.bounds = {
      min: { x: 0, y: 0 },
      max: { x: window.innerWidth, y: window.innerHeight }
    };
  });

  // Создаем падающие объекты
  createFallingObjects();

  // Обновляем и запускаем физику
  Engine.run(engine);
}


// сначала загрузить matter.js
// упаковать ссылки на картинки в массив images
// вызвать setupCanvasAndPhysics(images);
// const images = [
//   'https://www.ccsidbb.catholic.edu.au/wp-content/uploads/2020/06/Shrek.jpg',
//   'https://www.ccsidbb.catholic.edu.au/wp-content/uploads/2020/06/Shrek.jpg',
//   'https://www.ccsidbb.catholic.edu.au/wp-content/uploads/2020/06/Shrek.jpg',
//   'https://www.ccsidbb.catholic.edu.au/wp-content/uploads/2020/06/Shrek.jpg',
//   'https://www.ccsidbb.catholic.edu.au/wp-content/uploads/2020/06/Shrek.jpg'
// ];
// // Вызов функции с массивом ссылок на изображения
// setupCanvasAndPhysics(images,3);
