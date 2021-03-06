
function ShowMessage(_message, _type, _needed_for_wait) {

	const currMess = $('.js-adm_message_template').clone(true).appendTo('body');
	currMess.removeClass('js-adm_message_template');
	currMess.addClass('adm_message_show');
	currMess.find('.js-adm-message-text').text(_message);
	let _Class = '';
	if (_type === 'ok') { _Class='adm_message_ok'}
	if (_type === 'error') { _Class='adm_message_error'}
	if (_type === 'warning') { _Class='adm_message_warning'}
	currMess.addClass(_Class);

	let _Seconds = _needed_for_wait, int;
	currMess.find('.adm_message_seconds').text(_Seconds);
	int = setInterval(function() { // запускаем интервал
	  if (_Seconds > 0) {
		_Seconds--; // вычитаем 1
		 currMess.find('.adm_message_seconds').text(_Seconds); // выводим получившееся значение в блок
	  } else {
		clearInterval(int); // очищаем интервал, чтобы он не продолжал работу при _Seconds = 0
		currMess.remove();
	  }
	}, 1000);

}
	

$(document).ready(function () {

	const api_url = "/api";

	// Set CSRF token
	const token = $("meta[name='_csrf']").attr("content");
	const header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr) {
		xhr.setRequestHeader(header, token);
	});


	ajaxCall(api_url, {type: "contents"});
	const body = $("body");

	// OPEN-CLOSE SEARCH 
	$('.js-open-search').click(function (e) {
		e.preventDefault();

		const section_search = $('.section_search');
		if (section_search.hasClass('open'))
		{
			section_search.removeClass('open');
			ajaxCall(api_url, {type: "contents"});
			$('.js-open-search').removeClass('icon-cross');
			
		}
		else
		{
			section_search.addClass('open');
			$('.cards-items').html('');
			$('.js-open-search').addClass('icon-cross');
			const item_f_search = $('.item_f_search');
			item_f_search.val('');
			item_f_search.focus();
		}

	});
	
	
	// PRESS KEY IN SEARCH 
	
	$('.item_f_search').keyup(function(){

		const item_f_search = $('.item_f_search');
		const Value = item_f_search.val();

		if (Value.length > 2)
	  {
			$('.cards-items').html('');
			
			if (item_f_search.hasClass('SearchByText'))
			{
				ajaxCall(api_url, {type: "search", text: Value});
			}
			else
			{
				ajaxCall(api_url, {type: "search_head", text: Value});
			}
	  }
	 
	});

	//Изменение области поиска
	$('.js-check').change(function () {
		
		let item_f_search = $('.item_f_search');
		item_f_search.val('');
		item_f_search.focus();

		const radioBtn = $('input[name=SearchArea]:checked').val();

		if (radioBtn === 'SearchByHeader')
		{
			item_f_search.removeClass('SearchByText');
			item_f_search.addClass('SearchByHeader');
		}
		if (radioBtn === 'SearchByText')
		{
			item_f_search.removeClass('SearchByHeader');
			item_f_search.addClass('SearchByText');
		}

	});
	
	
	//DELETE
		
	body.on("click", ".js-delete", function (e) {
		e.preventDefault();

		const needToDel = parseInt($('.js-delete').attr('data-id'));

		hidePopup();
			// Сохраняем
			ajaxCall(api_url, {type: "delete", id: needToDel})
				.then(function () {
					ajaxCall(api_url, {type: "contents"})
				});

	});	
	
	body.on("click", ".cards-item .cards-item-inner .icon-cross", function (e) {
		e.preventDefault();

		const needToDel = parseInt($(this).parents('.cards-item').attr('data-id'));

		hidePopup();
			// Сохраняем
			ajaxCall(api_url, {type: "delete", id: needToDel})
				.then(function () {
					ajaxCall(api_url, {type: "contents"})
				});

	});
	


	//SAVE or EDIT

	$('.item_f_head').keydown(function(e) {
		if(e.keyCode === 13) {
		  e.preventDefault();
		  $('.item_f_text').focus();
		}
		if(e.keyCode === 27) {
			e.preventDefault();
			hidePopup();
		}
    });

	$('.item_f_text').keydown(function(e) {
		if(e.keyCode === 27) {
			e.preventDefault();
			hidePopup();
		}
    });
  
	body.on("click", ".js-save", function (e) {
		e.preventDefault();

		const header = $('.item_f_head').val();
		const item_f_text = $('.item_f_text');
		const bodyText = item_f_text.val();

		if (bodyText === '')
		{
			item_f_text.focus();
			$('.item_f_alert').text('Заполните заметку!');
			ShowMessage('Заполните заметку!', 'error', 3 );
			setTimeout(function () {
				$('.item_f_alert').text('');
			}, 3000)
		}
		else
		{
			hidePopup();

			// Сохраняем
			 if ($('.js-save').hasClass('new') )
			 {
				 ajaxCall(api_url, {type: "addnote", heading: header, note: bodyText})
					 .then(function() {
					 	ajaxCall(api_url, {type: "contents"})
					 });
			 }
			 else
			 {
				 const myid = parseInt($('.js-delete').attr('data-id'));

				 ajaxCall(api_url, {type: "edit", heading: header, note: bodyText, id: myid})
					  .then(function() {
					  	ajaxCall(api_url, {type: "contents"})
					  });
			 }
			
		}

	});


	//POPUP

	body.on("click", "a[data-window], span[data-window], button[data-window], input[data-window], div[data-window]", function (e) {
		e.preventDefault();
		
		if (!$(e.target).closest(".icon-cross").length) {
			
			const item_f = $('.item_f');
			const item_f_head = $('.item_f_head');
			item_f.val('');
			item_f_head.val('');
			
			const shadow = $('.shadow');
			shadow.removeClass('open');
			$('.popup').removeClass('open');

			//Закрываем меню
			shadow.removeClass('open');
			$('.mobile-menu').removeClass('open');
			$('.js_open_mobile').removeClass('open');

			const scrolltp = document.documentElement && document.documentElement.scrollTop || document.body.scrollTop;
			body.attr("data-scroll", scrolltp);
			body.addClass("body__menu_open");
			body.css('margin-top', '-' + scrolltp + 'px');
			const mypopup = $('.' + $(this).attr('data-window'));
			const thatID = $(this).attr('data-id');

			$('.js-delete').attr('data-id', thatID);
			mypopup.addClass("open");

			shadow.addClass('open');

			shadow.scrollTop(0);
			$('.shadow_scroll').scrollTop(0);
			
			if ( $(this).hasClass('js-new')) 
			{
				item_f.val('');
				item_f_head.val('');
				item_f_head.focus();
				$('.js-save').addClass('new');
			
			}
			else
			{
				ajaxCall(api_url, {type: "note", id: $(this).attr('data-id')});
				$('.js-save').removeClass('new');
				
			}
		}
		else
		{
			
		}

	});


	//POPUP_CLOSE

		function hidePopup(){
	
			$('.shadow').removeClass('open');
			$('.popup').removeClass('open');
			$('body').removeClass('open');
			if (body.hasClass("body__menu_open")) {
				const needed_scroll = body.attr("data-scroll");
				body.removeClass("body__menu_open");
				body.css('margin-top', '-' + 0 + 'px');
				$(window).scrollTop(needed_scroll);

			}
		}
	

	$(document).on('click',  function (e) {
	
	
		if (!$(e.target).closest("a[data-window], span[data-window], button[data-window], input[data-window], div[data-window],.popup_content,.burger_nav,.js-navblock_menu,.preloader,.click-circle").length) {
	
		hidePopup();
		}
	
	});
	

	$('.js-closepopup').click(function (e) {
		e.preventDefault();
		$('.shadow').removeClass('open');
		$('.popups').removeClass('open');
		const needed_scroll = body.attr("data-scroll");
		body.removeClass("body__menu_open");
		body.css('margin-top', '-' + 0 + 'px');
		$(window).scrollTop(needed_scroll);

	});
	
		$('.js-close-adm-message').click(function (e) {
		e.preventDefault();
		$('.js-adm_message').removeClass('adm_message_show');
			});
			
	
	function ajaxCall(api_url, body_data) {
		return $.ajax({
			url: api_url,
			type: "POST",
			dataType: "json",
			data: body_data,
		}).then(function (data) {
			if (data.result === 'ok') {
				if (body_data.type === 'contents') {
					$('.cards-items').html(data.value);
					console.log("contents");
					//ShowMessage('Список обновлен', 'ok', 1 );
				}
				if (body_data.type === 'search_head') {
					$('.cards-items').html(data.value);
					console.log('search_head');
				}

				if (body_data.type === 'search') {
					$('.cards-items').html(data.value);
					console.log('search');
				}

				if (body_data.type === 'delete') {
					console.log('delete');
					ShowMessage('Заметка удалена', 'ok', 3);
				}

				if (body_data.type === 'addnote') {
					console.log('addnote');
					ShowMessage('Заметка добавлена', 'ok', 3);
				}

				if (body_data.type === 'edit') {
					console.log('edit');
					ShowMessage('Заметка изменена', 'ok', 3);
				}


				if (body_data.type === 'note') {
					const heading = data.heading;
					const mytext = data.text;

					$('.item_f').val(mytext);
					const item_f_head = $('.item_f_head');
					item_f_head.val(heading);
					item_f_head.focus();

					console.log("note");
				}
			}
			if (data.result === 'error') {
				console.log('error');
				ShowMessage(data.value, 'error', 5);
			}

		});
    }

});
