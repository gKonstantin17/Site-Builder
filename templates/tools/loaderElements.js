function loaderElements(jsonArray) {
    var body = document.querySelector('body'); // Находим body

    // Удаляем старую секцию и контейнер
    var oldSection = document.querySelector('section');
    if (oldSection) {
        body.removeChild(oldSection);
    }

    // Функция для загрузки элементов с вложенностью
    function createElementFromJSON(item) {
        // Создаем элемент указанного тега (по умолчанию div)
        const elementTag = item.tag || 'div'; // Если тег не указан, используем div
        const element = document.createElement(elementTag);

        // Устанавливаем ID
        if (item.id) {
            element.id = item.id;
        }

        // Применяем стили из JSON
        if (item.styles) {
            // Если стили были сохранены как строка CSS
            if (typeof item.styles === 'string') {
                const styleObj = cssTextToObject(item.styles);
                Object.keys(styleObj).forEach(style => {
                    element.style[style] = styleObj[style];
                });
            }
            // Если стили уже в объекте (для упрощения обработки)
            else {
                Object.keys(item.styles).forEach(style => {
                    element.style[style] = item.styles[style];
                });
            }
        }

        // Добавляем контент (для обычных элементов)
        if (item.content && elementTag !== 'input') {
            element.textContent = item.content;
        }

        // Добавляем значение для <input> элементов
        if (elementTag === 'input' && item.value !== undefined) {
            element.value = item.value;
        }

        // Для div, если у элемента есть атрибут value, устанавливаем его
        if (elementTag === 'div' && item.value !== undefined) {
            element.setAttribute('value', item.value);
        }

        // Добавляем указанные классы
        if (item.classes && Array.isArray(item.classes)) {
            item.classes.forEach(className => {
                element.classList.add(className);
            });
        }

        // Добавляем "ручки" для изменения размера только для div и form
        const resizers = [
            'top', 'bottom', 'left', 'right', // Стороны
            'top-left', 'top-right', 'bottom-left', 'bottom-right' // Углы
        ];

        if ((elementTag === 'div' || elementTag === 'form') && item.children && !item.classes.includes('no-resizers')) {
            resizers.forEach(resizer => {
                const resizerDiv = document.createElement('div');
                resizerDiv.classList.add('resizer', resizer);
                if (item.id) {
                    resizerDiv.dataset.id = item.id; // Привязываем ID к каждой "ручке"
                }
                element.appendChild(resizerDiv);
            });
        }

        // Если есть дочерние элементы, добавляем их рекурсивно
        if (item.children && Array.isArray(item.children)) {
            item.children.forEach(child => {
                const childElement = createElementFromJSON(child);
                element.appendChild(childElement);
            });
        }

        return element;
    }

    // Создаем элементы из jsonArray
    jsonArray.forEach(item => {
        const element = createElementFromJSON(item);
        body.appendChild(element);
    });
}

// Функция для парсинга строки CSS в объект
function cssTextToObject(cssText) {
    const styleObj = {};
    const styles = cssText.split(';').map(style => style.trim()).filter(Boolean);

    styles.forEach(style => {
        const [property, value] = style.split(':').map(part => part.trim());
        if (property && value) {
            styleObj[property] = value;
        }
    });

    return styleObj;
}
