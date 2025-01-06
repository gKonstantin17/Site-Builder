function createHeader(number) {
  // Проверяем, существует ли уже header
  const existingHeader = document.querySelector('header');
  if (existingHeader) {
      return; // Если header существует, ничего не делаем
  }

  // Создаем элемент header
  const header = document.createElement('header');
  const headerId = crypto.randomUUID();
  header.setAttribute('id', headerId);
  
  // Создаем блок с ссылками
  const headerLinks = document.createElement('div');
  headerLinks.classList.add('header-links');
  
  // Генерация p элементов в соответствии с числом, переданным в функцию
  for (let i = 1; i <= Number(number); i++) {
    const p = document.createElement('p'); // Создаем <p> элемент
    const pId = crypto.randomUUID();
    p.setAttribute('id', pId);
    p.textContent = `Страница ${i}`; // Устанавливаем текст элемента

    // Добавляем элемент в блок
    headerLinks.appendChild(p);
  }
  
  // Создаем контейнер header с классом container-header
  const containerHeader = document.createElement('div');
  containerHeader.classList.add('container-header');
  
  // Добавляем блок с p элементами в контейнер
  containerHeader.appendChild(headerLinks);
  
  // Добавляем контейнер в header
  header.appendChild(containerHeader);
  
  // Добавляем header в body
  const body = document.body;
  
  // Добавляем header перед .container, если контейнер существует
  const existingContainer = body.querySelector('section');
  if (existingContainer) {
    body.insertBefore(header, existingContainer);
  } else {
    // Если контейнера нет, просто добавляем header в body
    body.appendChild(header);
  }

  // Применение стилей через JavaScript

  // Стили для container-header
  containerHeader.style.margin = '0 auto';
  containerHeader.style.width = '1140px';
  
  // Стили для header
  header.style.backgroundColor = '#fefefe';
  header.style.borderBottom = 'black solid 1px';
  
  // Стили для header-links (используем flexbox)
  headerLinks.style.display = 'flex';
  headerLinks.style.width = '100%';
  headerLinks.style.justifyContent = 'space-around';
  headerLinks.style.padding = '10px 0px 10px 0px';
  
  // Стили для p (оставляем как для кнопок, но без функциональности кнопки)
  const ps = headerLinks.querySelectorAll('p');
  ps.forEach(p => {
    p.style.display = 'block';   // Блокируем каждый p
    p.style.fontSize = '24px';    // Размер шрифта
    p.style.color = '#000000';    // Цвет текста
    p.style.cursor = 'pointer';  // Курсор при наведении
    p.style.marginRight = '20px'; // Отступы между элементами
    p.style.textAlign = 'center'; // Выравнивание текста по центру
    p.style.padding = '5px 10px'; // Внешний отступ для "кнопок"
  });
}

// Пример вызова функции
//createHeader(3); 
