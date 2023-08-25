export const userInfoParams =[{
    title:'姓名',
    key:'name',
    type:'input',
    rule:{rules:[{ required: true, message:'请输入姓名' }]}
},{
    title:'性别',
    key:'sex',
    type:'radio',
    rule:{rules:[{ required: true, message:'请选择性别' }]}
},{
    title:'生日',
    key:'birthday',
    type:'date',
    rule:{rules: [{ type: 'object', required: true, message: '请选择生日' }]}
},{
    title:'籍贯',
    key:'area',
    type:'cascader',
    rule:{rules: [{ required: true, message: '请选择角色', type: 'array' }]}
    
},{
    title:'手机号',
    key:'phone',
    type:'input',
    rule:{
        rules:[
            {required:true, message:'手机号不能为空'},
		    {pattern:/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/, message:'请输入正确的手机号'}
        ]
    }
},{
    title:'邮箱',
    key:'email',
    type:'input',
    rule:{
        rules: [
            {type: 'email',message: '请输入正确的邮箱格式',},
            { required: true,message: '请输入邮箱',},
        ],
        
   }
},{
    title:'角色',
    key:'role',
    type:'select',
    rule:{rules: [{ required: true, message: '请选择角色' }]}
},{
    title:'头像',
    key:'avatar',
    type:'photo',
},]