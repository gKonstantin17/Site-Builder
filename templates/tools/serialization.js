(function serializeBlocks() {
    // Рекурсивная функция для сериализации элементов и их вложенных элементов
    function serializeElement(element, seenElements = new Set()) {
        // Пропускаем элементы с "ручками" изменения размера
        if (element.classList.contains('snow-blocks') || element.classList.contains('canvas-container') || element.classList.contains('canvas-box') ||element.classList.contains('resizer') || element.nodeName.toLowerCase() === 'script') {
            return null;
        }

        // Пропускаем элементы, которые уже были сериализованы (для предотвращения дублирования)
        if (seenElements.has(element)) {
            return null;
        }
        seenElements.add(element);

        const { id, textContent, tagName, classList, style } = element;

        // Определяем content для разных типов элементов
        let content = null;
        let value = null;

        // Если это элемент <input>, сохраняем его значение в value
        if (tagName.toLowerCase() === 'input') {
            value = element.value || null; // Получаем value для input
        }
        // Если это элемент <div>, проверяем наличие атрибута value для сохранения
        else if (tagName.toLowerCase() === 'div') {
            value = element.getAttribute('value') || null; // Получаем значение из атрибута value для div
        } else {
            // Если это обычный элемент, сохраняем текстовое содержимое в content
            content = textContent.trim();

            // Устанавливаем content как null для элементов section и container
            if (tagName.toLowerCase() === 'section' || tagName.toLowerCase() === 'div' || tagName.toLowerCase() === 'header') {
                content = null;
            }
        }

        // Сериализуем текущий элемент
        const serializedElement = {
            id: id || null, // Если id нет, сохраняем null
            tag: tagName.toLowerCase(), // Название тега в нижнем регистре
            classes: Array.from(classList), // Преобразуем DOMTokenList в массив
            content: content, // Устанавливаем content (или null для input)
            value: value, // Сохраняем value для input
            styles: style.cssText || null, // Все инлайновые стили как строка
            children: [] // Массив для хранения дочерних элементов
        };

        // Рекурсивно обрабатываем дочерние элементы
        element.childNodes.forEach(child => {
            if (child.nodeType === 1) { // Если это элемент (не текст или комментарий)
                const serializedChild = serializeElement(child, seenElements);
                if (serializedChild) {
                    serializedElement.children.push(serializedChild); // Добавляем в children
                }
            }
        });

        return serializedElement;
    }

    // Находим все элементы внутри body
    const allElements = document.querySelectorAll('body > *'); // Только прямые дочерние элементы body

    // Создаем массив для хранения данных всех элементов
    const serializedElements = [];

    allElements.forEach(element => {
        // Для каждого элемента вызываем рекурсивную функцию
        const serialized = serializeElement(element);
        if (serialized) {
            serializedElements.push(serialized);
        }
    });

    // Преобразуем массив объектов в JSON-строку
    const serializedJSON = JSON.stringify(serializedElements, null, 2);

    // Выводим результат в консоль
    console.log(serializedJSON);

    // Отправляем данные через window.cefQuery
    if (window.cefQuery) {
        window.cefQuery({
            request: "serMessage: " + JSON.stringify(serializedElements),
            onSuccess: function(response) {
                console.log("Данные успешно отправлены в Java.");
            },
            onFailure: function(error_code, error_message) {
                console.error("Ошибка отправки в Java:", error_message);
            }
        });
    } else {
        console.log("window.cefQuery не доступен. JSON данные:", serializedJSON);
    }
})();
