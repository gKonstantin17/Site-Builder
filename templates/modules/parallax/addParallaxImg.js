function addParallaxImg(imageUrl,value) {

    const id = crypto.randomUUID(); // Генерируем уникальный ID
    if (!imageUrl) {
        return; // Прекращаем выполнение функции, если параметр не передан
    }

    // Создаем div-обертку
    var newDiv = document.createElement('div');
    newDiv.id = id; // Устанавливаем уникальный ID
    newDiv.style.backgroundImage = `url('${imageUrl}')`; // Устанавливаем изображение как фон
    newDiv.style.backgroundSize = 'cover'; // Растягиваем изображение на весь блок
    newDiv.style.backgroundPosition = 'center'; // Центрируем изображение
    newDiv.style.backgroundRepeat = 'no-repeat'; // Запрещаем повторение фона
    newDiv.setAttribute('value', value);

    // Стили для блока
    newDiv.style.width = '200px';
    newDiv.style.height = '150px';
    newDiv.style.position = 'absolute'; // Для позиционирования
    newDiv.style.border = '1px solid #ccc';
    newDiv.style.overflow = 'hidden';
    newDiv.className = 'mouse';

    // Добавляем "ручки" для изменения размера
    const resizers = [
        'top', 'bottom', 'left', 'right', // Стороны
        'top-left', 'top-right', 'bottom-left', 'bottom-right' // Углы
    ];

    resizers.forEach(resizer => {
        var resizerDiv = document.createElement('div');
        resizerDiv.classList.add('resizer', resizer);
        resizerDiv.dataset.id = id; // Привязываем ID к каждой "ручке"
        newDiv.appendChild(resizerDiv);
    });

    if (selectedElementId) {
        const container = document.getElementById(selectedElementId);
        container.appendChild(newDiv);
    } else {
        const container = document.querySelector('.container');
        container.appendChild(newDiv);
    }
        
}
