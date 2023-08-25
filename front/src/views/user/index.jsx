import React,{Component} from "react";
import { Card,Button,Table, message,Avatar, Tag,Divider,Popconfirm,Modal,Pagination } from "antd";
import '@/styles/user.less'
import moment from 'moment'
import ModalForm from "./components/ModalForm";
import SearchForm from "./components/SearchForm";
import { userInfoParams } from "./userInfoParams";
import { getDictListApi } from "../../api/dict";
import { addUser,editUser,delUser,getUserListApi } from "../../api/user";
import { areaParams } from "./areaParams";
import TablePagination from "./components/tablePagination";
const { confirm } = Modal;
class User extends Component{
    state={
        userModalFormVisible:false,
        userModalFormTitle:'',
        userModalFormLoading:false,
        userModalFormData:{ },
        roleList:[],
        tableData:[],
        columns:[],
        delVisible:false
    }

    
    /**
     * 初始化数据
     */
    initData(){
        let obj={}
        let list=[]
        list.push({
            title:userInfoParams[userInfoParams.length-1].title,
            key:0,
            dataIndex:userInfoParams[userInfoParams.length-1].key,
            render:(item,record)=>{
                console.log('头像===',record);
                if(!!item){
                    return(
                        <Avatar size={"large"}  src={item}  /> 
                    )
                }else{
                    return(
                        <Avatar size={"large"} style={{ color: '#f56a00', backgroundColor: '#fde3cf' }}> {record.name}</Avatar>
                    )
                }
                
            }
        })
        // 得到表单和表格对象
        userInfoParams.forEach((item,index)=>{
            obj[item.key]=undefined
            if(index!=userInfoParams.length-1){
                if(item.key=='role'){
                    list.push({
                        title:item.title,
                        key:index,
                        dataIndex:item.key,
                        render:item=>{
                            switch (item) {
                                case 'admin':
                                    return (<Tag color="#ff4d4f">管理员</Tag>)
                                case 'common':
                                    return (<Tag color="#4096ff">普通员工</Tag>)
                                case 'pm':
                                    return (<Tag color="#ffc53d">产品经理</Tag>)
                                default:
                                    break;
                            }
                        }
                    })
                }else if(item.key=='area'){
                    list.push({
                        title:item.title,
                        key:index,
                        dataIndex:item.key,
                        render:item=>{
                            let areaArr=item.split(',')
                            let areaStr=`${areaParams.find(item=>item.value==areaArr[0]).label}/${areaParams.find(item=>item.value==areaArr[0]).children.find(oneItem=>oneItem.value==areaArr[1]).label}/${areaParams.find(item=>item.value==areaArr[0]).children.find(oneItem=>oneItem.value==areaArr[1]).children.find(twoItem=>twoItem.value==areaArr[2]).label}`
                           return (<div>{areaStr}</div>)
                        }
                    })
                }else  if(item.key=='sex'){
                    list.push({
                        title:item.title,
                        key:index,
                        dataIndex:item.key,
                        render:item=>{
                            return (<div>{['','男','女'][item]}</div>)
                        }
                    })
                }else if(item.key=='birthday'){
                    list.push({
                        title:item.title,
                        key:index,
                        dataIndex:item.key,
                        render:item=>{
                            return (<div>{moment(item).format('YYYY-MM-DD')}</div>)
                        }
                    })
                }else{
                        list.push({
                            title:item.title,
                            key:index,
                            dataIndex:item.key,
                        
                        })
                    }
            }
            
        })

        // 获取角色字典
        getDictListApi({type:'role'}).then(res=>{
            this.setState(
                ()=>{
                    return {userModalFormData:obj,roleList:res.data,columns:list}
                },
                ()=>{console.log('this.state',this.state)}
            )
        })
       
        list.push({
            title:'操作',
            key:userInfoParams.length,
            render:(item,record)=>{
                return(
                    <div>
                        <Button  type="primary" shape="circle" icon="edit" title="编辑" onClick={()=>{
                            console.log('record-===',record);
                            this.handleOpenEdit(record)
                        }}/>
                        <Divider type="vertical" />
                        <Button type="danger" shape="circle" icon="delete" title="删除" onClick={(exent)=>this.onDel(exent,record)}/>
                        
                        
              
                    </div>
                )
                
            }
        })

       
    }
    /**
     * 删除记录
     */
    onDel=(event,record)=>{
        event.preventDefault();
        delUser({id:record.id}).then(res=>{
            message.success("删除成功！")
            this.getUserList()
        })
    }
    onDelCancel=()=>{

    }

    /**
     * 获取用户列表
     */
    getUserList=(searchVal)=>{
        console.log('this.paginationRef=',this.paginationRef.state.pageVal);
        getUserListApi({...this.paginationRef.state.pageVal,...searchVal}).then(res=>{
            this.paginationRef.setTotal(res.data.total)
            this.setState({
                tableData:res.data.records
            })
        })
    }
    /**
     * 打开表单
     */
    handleOpenModal=_=>{
        let obj={}
        if(!!this.state.userModalFormData.avatar){
            
            userInfoParams.forEach((item,index)=>{
                obj[item.key]=undefined
            })
            this.setState({
                userModalFormData:obj
            })
        }
        
        setTimeout(() => {
            this.setState({
                userModalFormVisible:true,
                userModalFormTitle:'添加用户信息',
            })
            console.log('我要看看--------',this.state);
        }, 100);
    }
    /**
     * 
     * 关闭表单
     */
    handleCancelModal=_=>{
        this.userFormRef.props.form.resetFields();
        this.setState({
            userModalFormVisible:false
        })
    }
    /**
     * 提交表单数据
     */
    handleSubmitFormData=_=>{
        const {form} =this.userFormRef.props
        
        
        form.validateFields((err, values) => {

            console.log('values=',values);
            let obj={
                avatar: typeof values.avatar!='object'?values.avatar:values.avatar.file.response.message,
                area:values.area.toString(),
                id:this.state.userModalFormTitle.indexOf('添加')!=-1?undefined:this.state.tableData.find(item=>item.name==values.name).id
            }
            
            console.log('obj=',obj);
            if (err) {
                message.warning("不通过！")
              return;
            }
            this.setState({ userModalFormLoading: true, });
            let api=this.state.userModalFormTitle.indexOf('添加')!=-1?addUser:editUser
 
            api({...values,...obj}).then((res) => {
              form.resetFields();
              this.setState({ userModalFormVisible: false, userModalFormLoading: false });
              message.success("操作成功!")
              this.getUserList()
            }).catch(e=>{
                message.warning(e)
            })
          });
    }
    
    /**
     * 打开编辑表单
     */
    handleOpenEdit=(record)=>{
        console.log('record=',record);
        this.setState({
            userModalFormVisible:true,
            userModalFormTitle:'修改用户信息',
            userModalFormData:record
        })
        const {form} =this.userFormRef.props
        setTimeout(() => {
            form.setFieldsValue({
                ...record,
                birthday:moment(record.birthday),
                area:record.area.split(","),
            })
        }, 100);

    }
    /**
     * 删除操作
     */
    handleDel=(id)=>{
        message.success("删除成功！")
        // delUser({id}).then(res=>{
        //     message.success("删除成功！")
        // })
    }
    delThinkTwice=(record,val)=>{
        confirm({
            title: '确定删除?',
            content: `姓名：${record.name},角色：${record.role}`,
            onOk() {
              delUser({id:record.id}).then(res=>{
                message.success("删除成功！")
                getUserListApi({...val}).then(res=>{
                    
                    this.setState({
                        tableData:res.data.records
                    })
                })
              })
            },
            onCancel() {},
          });
    }
    onShowSizeChange=(val)=>{
        this.getUserList()
    }
    onSearch=_=>{
        const {form} =this.userSearchFormRef.props
        form.validateFields((err, values) => {
            console.log('我都values=',values);
            let obj={
                name:!!values.name?values.name:undefined,
                birthMonth:!!values.birthday?moment(values.birthday).format('MM'):undefined,
                role:!!values.role?values.role:undefined
            }

            this.getUserList(obj)
        })
        
    }
    onReset=_=>{
        const {form} =this.userSearchFormRef.props
        form.resetFields()
        this.getUserList()

    }
    componentDidMount() {
        this.initData()
        this.getUserList()
    }
    
render(){
    return (
        <div >
             <Card className="card-layout" >
                <SearchForm 
                 wrappedComponentRef={formRef => this.userSearchFormRef = formRef}
                searchKey={['name','birthday','role']}
                roleList={this.state.roleList}
                onSearch={this.onSearch}
                onReset={this.onReset}
                />
            </Card>
            <Card className="card-layout" extra={<Button type="primary" onClick={this.handleOpenModal}>添加用户信息</Button>}>
                <Table columns={this.state.columns} dataSource={this.state.tableData} pagination={false} rowKey="name" scroll={{  y: 900 }}/>
              <TablePagination ref={ref=>this.paginationRef=ref} onShowSizeChange={this.onShowSizeChange.bind(this)}/>
            </Card>
            <ModalForm
             wrappedComponentRef={formRef => this.userFormRef = formRef}
            visible={this.state.userModalFormVisible}
            title={this.state.userModalFormTitle}
            roleList={this.state.roleList}
            confirmLoading={this.state.userModalFormLoading}
            onCancel={this.handleCancelModal}
            onOk={this.handleSubmitFormData}
            userModalFormData={this.state.userModalFormData}
            />
        </div>
    );
}
}

export default User