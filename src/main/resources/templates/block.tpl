notes.each { note ->
    newLine()
    yieldUnescaped """
    <div class="cards-item" data-window="popup_example" data-id=${note.id}>

        <div class="cards-item-data">
            <div class="cards-item-data-day">${note.timestamp.date}</div>
            <div class="cards-item-data-mes">${month[note.timestamp.month]}</div>
        </div>

        <div class="cards-item-inner">
            <div class="cards-item-name">${note.heading ?: note.note}</div>
            <div class="cards-item-val">${note.note}</div>

        </div>

    </div>
"""
}