notes.each { note ->
    newLine()
    yieldUnescaped """
    <div class="cards-item" data-window="popup_editor" data-id=${note.id}>

        <div class="cards-item-data">
            <div class="cards-item-data-day">${note.timestamp.date}</div>
            <div class="cards-item-data-mes">${month[note.timestamp.month]}</div>
            <div class="cards-item-data-time">${note.timestamp.hours+':'+note.timestamp.minutes}</div>
        </div>

        <div class="cards-item-inner">
            <div class="cards-item-name">${escape.escapeHtml(note.heading ?: note.note.take(50))}</div>
            <div class="cards-item-val">${escape.escapeHtml(note.note.take(100))}</div>
            <span class="icon icon-cross"></span>
        </div>

    </div>
"""
}