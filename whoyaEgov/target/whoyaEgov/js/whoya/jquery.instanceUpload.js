/**
 * @author 김동규
 * @since 2012.04.27
 * @dependency jquery.core
 * @dependency jquery.form
 * 
 * ==use==
 * $([selector]).instanceUpoad(options);
 * 
 * ==options==
 * optoins = {
 * 		type:			'image'				//file 일 경우 모든 이미지 관련 옵션은 무시(file | image)
 		,viewDivSuffix:	'View'				//view 영역의 식별을 위한 suffix. [upload div의 아이디 + viewDivSuffix] 가 view 영역의 ID가 된다. 
		,context:		''					//applicationContext. 본서버에 올라갈 경우 없어도 무관
		,uploadAction:	''					//업로드 처리를 수행할 URL
		,deleteAction:	''					//삭제 처리를 수행할 URL
		,thumbnailSize:	''					//생성될 thumbnail의 크기 [width]x[height] ex)66x66
		,noImage:		''					//이미지가 없을 때 표시할 image url
		//처리를 수행할 폼의 필드 매핑
		//각 값은 prefix로 동작한다. suffix는 0부터 시작하는 index가 된다.
		,match:	{							
			uploadBtn: 	'btn_upload'
			,filename:	'filename'
			,fileurl:	'fileurl'
			,thumbnail:	'thumbnail'
			,deleteBtn:	'btn_delete'
		}
		//파일 업로드를 위한 히든 폼의 필드 매핑, 서버에서 처리하기 위한 이름.
		//수정할 경우 서버쪽 파라메터명도 같이 수정해야 한다.
		,inputs: {							
			prefile: 	'prefile'			//이전 파일의 이름
			,thumbnail: 'thumbnail'			//thumbnail의 사이즈
			,file:		'file'				//업로드 될 파일
			,type:		'type'				//파일 타입
		}
		,iframe: 		'instanceUploadFrame'	//IE일 경우 생성되는 iframe의 id prefix
	}
 */
(function($) {
$.fn.instanceUpload = function(options) {
	
	var self = this;
	
	var defaults = {
		type:			'image'				//file 일 경우 모든 이미지 관련 옵션은 무시
		,viewDivSuffix:	'View'				//이미지 뷰 영역의 suffix. 이미지업로드 영역 ID뒤에 붙어서 뷰영역 아이디를 구성한다.
		,context:		''					//applicationContext. 본서버에 올라갈 경우 없어도 무관
		,uploadAction:	''					//업로드 처리를 수행할 URL
		,deleteAction:	''					//삭제 처리를 수행할 URL
		,thumbnailSize:	''					//생성될 thumbnail의 크기 [width]x[height] ex)66x66 
											//','로 여러개의 썸네일을 생성가능하다. ex)66x66,100x100 반환되는 썸네일 주소는 첫번째 항목이다.
		,noImage:		'/'					//이미지가 없을 때 표시할 image url
		//처리를 수행할 폼의 필드 매핑
		//각 값은 prefix로 동작한다. suffix는 0부터 시작하는 index가 된다.
		,match:	{							
			uploadBtn: 	'btn_upload'
			,filename:	'filename'
			,fileurl:	'fileurl'
			,thumbnail:	'thumbnail'
			,deleteBtn:	'btn_delete'
		}
		,inputs: {							//파일 업로드를 위한 히든 폼의 필드 매핑, 서버에서 처리하기 위한 이름.
			prefile: 	'prefile'			//이전 파일의 이름
			,thumbnail: 'thumbnail'			//thumbnail의 사이즈
			,file:		'file'				//업로드 될 파일
			,type:		'type'
		}
		,iframe: 		'instanceUploadFrame'
	};
	var idxInput = 'instanceUploadIdx';
	var o = $.extend(defaults, options);
	
	var viewDiv = $('#' + self.attr('id') + o.viewDivSuffix);
	
	var formId = 'instanceUpload' + new Date().getTime();
	
	var _css = {
		position: 		'absolute'
		,top:			'-1000px'
		,left:			'-1000px'
		,visibility: 	'hidden'
	};

	var doc;
	
	var _build = function() {
		if ($.browser.msie) { //ie 일 경우 멀티파트 폼의 자동 전송 동작이 액세스 거부되므로 iframe을 생성하여 처리한다.
			if (!$('#' + o.iframe).contents()[0] || !$('#' + o.iframe).contents().find('body')[0]) {
				setTimeout(function() {
					_build();
				}, 100);
				return;
			}
			
			doc = $('#' + o.iframe).contents().find('body');
		} else {
			doc = document.body;
		}

		var f = $('<form></form>')
			.css(_css)
			.attr({
				id: 		formId
				,method:	'post'
			})
			.appendTo(doc);
		$('<input/>').attr({
			id: 	idxInput
			,name: 	idxInput
			,type:	'hidden'
		}).appendTo(f);
		$('<input/>').attr({
			id: 	o.inputs.file
			,name: 	o.inputs.file
			,type:	'file'
		}).appendTo(f);
		$('<input/>').attr({
			id: 	o.inputs.thumbnail
			,name: 	o.inputs.thumbnail
			,type:	'hidden'
		}).appendTo(f);
		$('<input/>').attr({
			id: 	o.inputs.prefile
			,name: 	o.inputs.prefile
			,type:	'hidden'
		}).appendTo(f);
		$('<input/>').attr({
			id: 	o.inputs.type
			,name: 	o.inputs.type
			,type:	'hidden'
		}).appendTo(f);
	 	//$('<input type="submit"/>').appendTo(f);
		var _fileInput = f.find('#' + o.inputs.file).change(function(e, ui) {
	
			var idx = f[0][idxInput].value;
			
			//히든 폼 필드값 세팅
			f[0][o.inputs.prefile].value = self.find('#' + o.match.filename + idx)[0].tagName.toUpperCase()=='INPUT'? 
					self.find('#' + o.match.fileurl + idx).val() : self.find('#' + o.match.fileurl + idx).text();
			f[0][o.inputs.thumbnail].value = o.thumbnailSize;
			f[0][o.inputs.type].value = o.type;
			f.ajaxSubmit({
				dataType:	'json'
				,url: 		o.uploadAction
				,success: function(data, status, xhr) {
					eval('var d='+xhr.responseText);
	
					self.find('#' + o.match.fileurl + idx).val(d.fileurl);
					self.find('#' + o.match.filename + idx)[0].tagName.toUpperCase() == 'INPUT' ? 
							self.find('#' + o.match.filename + idx).val(d.filename) : 
								self.find('#' + o.match.filename + idx).text(d.filename);
					
					if (o.type=='image') {
						var viewUrl = '';
						if (parseInt(idx)!=0)
							viewUrl = d.thumbnailurl;
						else
							viewUrl = d.fileurl;
						
						if (o.context.length!=0) 
							viewUrl = o.context.replace(/\/$/,'') + viewUrl;
						
						viewDiv.find('#' + o.match.thumbnail + idx).attr('src', viewUrl);
					}
					
					f.resetForm();
				}
			});
			//
			
		});
		//upload button 이벤트 지정
		self.find('[id^=' + o.match.uploadBtn + ']').each(function(k, v) {
			
			if (v.tagName.toUpperCase() == 'A') {
				$(v).attr('href', 'javascript:void(0)');
			}
			$(v).click(function(e, ui) {
				var id = $(this).attr('id');
				f.find('#' + idxInput).val(id.replace(o.match.uploadBtn, ''));
				_fileInput.click();
			});
		});
		//delete button 이벤트 지정
		self.find('[id^=' + o.match.deleteBtn + ']').each(function(k, v) {
			
			if (v.tagName.toUpperCase() == 'A') {
				$(v).attr('href', 'javascript:void(0)');
			}
			
			$(v).click(function(e, ui) {
				
				var id = $(this).attr('id');
				f.find('#' + idxInput).val(id.replace(o.match.deleteBtn, ''));
				var idx = f[0][idxInput].value;
				
				if ($.trim(self.find('#' + o.match.filename + idx).val()).length==0) {
					alert('파일이 존재하지 않습니다.');
					return false;
				}
					 
				//히든 폼 필드값 세팅
				f[0][o.inputs.prefile].value = self.find('#' + o.match.filename + idx)[0].tagName.toUpperCase()=='INPUT'? 
						self.find('#' + o.match.fileurl + idx).val() : self.find('#' + o.match.fileurl + idx).text();
				f[0][o.inputs.thumbnail].value = o.thumbnailSize;
				f[0][o.inputs.type].value = o.type;
				
				f.ajaxSubmit({
					dataType:	'json'
					,url: 		o.deleteAction
					,success: function(data, status, xhr) {
						eval('var d='+xhr.responseText);
						
						switch(d.result) {
						case 'SUCCESS':
							self.find('#' + o.match.fileurl + idx).val('');
							self.find('#' + o.match.filename + idx)[0].tagName.toUpperCase() == 'INPUT' ? 
									self.find('#' + o.match.filename + idx).val('') : 
										self.find('#' + o.match.filename + idx).text('');
							
							if (o.type=='image') {
								var viewUrl = '';
								if (parseInt(idx)!=0)
									viewUrl = o.noImage;
								else
									viewUrl = o.noImage;
								
								viewDiv.find('#' + o.match.thumbnail + idx).attr('src', viewUrl);
								//$('#' + o.match.thumbnail + idx).attr('src', viewUrl);
							}
							break;
						case 'FAIL':
							break;
						}
						
						f.resetForm();
					}
				});
			});
		});
	};
	
	if ($.browser.msie) {
		$('<iframe height="0" width="0"></iframe>')
			.attr({
				id:		o.iframe
				,name:	o.iframe
			})
			.css(_css).appendTo(document.body);
	}
	_build();
};
})(jQuery);