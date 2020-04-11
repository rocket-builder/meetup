var custom = new Datepicker('#custom', {
    multiple: true,
    inline: true,

    classNames: {
      node: 'datepicker custom'
    },

    templates: {
      container: [
        '<div class="datepicker__container">',
          '<% for (var i = -1; i <= 1; i++) { %>',
            '<div class="datepicker__pane">',
              '<%= renderHeader(i) %>',
              '<%= renderCalendar(i) %>',
            '</div>',
          '<% } %>',
        '</div>'
      ].join('')
    }
  });