import { Modal,Form,Input, Button ,Radio,DatePicker,Select,Cascader,Upload,Icon, message} from "antd";
import React, { Component } from "react";
import { userInfoParams } from "../../userInfoParams";
import { areaParams } from "../../areaParams";
import { getToken } from "@/utils/auth";
import 'moment/locale/zh-cn';
import zhCN from 'antd/es/date-picker/locale/zh_CN'; // 引入中文包

const { Option } = Select;
const {MonthPicker}=DatePicker
class SearchForm extends Component {
    state={
        selectValue:1,
        imageUrl:undefined,
        fileList:[],
        formData:{}
    }
    /**
     * 级联值改变
     * @param {*} value 级联数组
     */
    onCascaderChange=(value)=> {
        console.log(value);
      }

    /**
     * 处理表单上传头像，仅上传一张头像
     */
    uploadButton=_=> {
    if(!!this.state.imageUrl){
        return (
            <img src={this.state.imageUrl} alt="avatar" style={{ width: '100%' }} /> 
        )
    }else{
        return (
            <div>
                <Icon type= 'plus' />
                <div className="ant-upload-text">请上传头像</div>
            </div>
            )
    }};

    /**
     * 头像上传前的操作
     */
    beforeUpload=(file)=> {
    console.log('file=',file);
    const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
    if (!isJpgOrPng) {
        message.error('You can only upload JPG/PNG file!');
    }
    const isLt2M = file.size / 1024 / 1024 < 2;
    if (!isLt2M) {
        message.error('Image must smaller than 2MB!');
    }
    return isJpgOrPng && isLt2M;
    }
    /**
     * 上传头像的进行时以及结果
     */
    handleFileChange = info => {
        if (info.file.status === 'done') {
        
            setTimeout(() => {
            this.setState({
                imageUrl:info.file.response.message,
                })
            }, 100);

        }

    let fileList = [...info.fileList];

    fileList = fileList.slice(-2);

    fileList = fileList.map(file => {
        if (file.response) {
        file.url = file.response.url;
        }
        return file;
    });

    this.setState({ fileList });
    };
    /**
     * 根据公共文件循环生成表单对象，减少重复
     * @param {*} item  表单对象
     * @param {*} index 坐标
     * @returns 
     */
    createFormItem=(item,index)=>{
        
        switch (item.type) {
            case 'input':
                return (
                    <Form.Item  label={item.title} key={index} >{
                        this.props.form.getFieldDecorator(item.key)( 
                            <Input placeholder={`请输入${item.title}`}></Input>
                        )}
                    </Form.Item>
                )
            case 'radio':
                return (<Form.Item label={item.title} key={index}  >{
                        this.props.form.getFieldDecorator(item.key)( 
                            <Radio.Group onChange={this.onChange}>
                            <Radio value={1}>男</Radio>
                            <Radio value={2}>女</Radio>
                            
                            </Radio.Group>
                        )}
                </Form.Item>)
            case 'date':
                return (<Form.Item label={item.title} key={index}  >{
                        this.props.form.getFieldDecorator(item.key)(
                            <MonthPicker locale={zhCN}  placeholder={`请选择${item.title}`} format={"M月"} />
                        )}
                </Form.Item>)
            case 'cascader':
                return (<Form.Item label={item.title} key={index}  >{
                    this.props.form.getFieldDecorator(item.key)(
                        <Cascader options={areaParams} onChange={this.onCascaderChange} showSearch="true" placeholder={`请选择${item.title}`} />
                    )}
            </Form.Item>)
            case 'select':
                return (<Form.Item label={item.title} key={index}>{
                    this.props.form.getFieldDecorator(item.key)( 
                            <Select placeholder={`请选择${item.title}`} className="select-layout">
                                {
                                    this.props.roleList.map((item,index)=>{return <Option key={index} value={item.name}>{item.remark}</Option>})
                                }
                            </Select>
                        )}
                    </Form.Item>)
            case 'photo':
                return (
                    <Form.Item label={item.title} key={index}>{
                        this.props.form.getFieldDecorator(item.key)( 
                            <Upload
                                name="avatar"
                                listType="picture-card"
                                className="avatar-uploader"
                                showUploadList={false}
                                action="http://127.0.0.1:9002/upload/files"
                                headers={
                                    {
                                        Authorization: 'Bearer ' + getToken(),
                                    }
                                  }
                                beforeUpload={this.beforeUpload}
                                onChange={this.handleFileChange}
                                fileList={ this.state.fileList} 
                          >
                            {this.uploadButton()}
      
                          </Upload>
                            )}
                    </Form.Item>
                )
                
            default:
                return (
                    <Form.Item 
                    label={item.title} 
                    name={item.key}  
                    key={index} 
                    rule={[{ required: true, message:`请输入${item.title}` }]}
                    >
                       <Input placeholder={`请输入${item.title}`}></Input>

                    </Form.Item>
                );
        }
    }
    
    initData(){
        this.setState({
            formData:this.props.userModalFormData
        })
        console.log('this.state111111111111111111111=',this.state);
        console.log(' userInfoParams.filter(filterItem=>searchKey.indexOf(filterItem.key)==-1)', userInfoParams.filter(filterItem=>this.props.searchKey.indexOf(filterItem.key)!=-1));
    }
    componentDidMount(){
        this.initData()
    }
    render(){
        const { searchKey,onSearch,onReset} = this.props;
        return(
            <Form layout="inline" >
                {
                    userInfoParams.filter(filterItem=>searchKey.indexOf(filterItem.key)!=-1).map((item,index)=>{
                        return this.createFormItem(item,index)
                    })
                    
                }
                <Form.Item  key={userInfoParams.length}  >
                    <Button type="primary" onClick={onSearch}>查询</Button>
                    <Button onClick={onReset} className="reset-btn">重置</Button>
                </Form.Item>
            </Form>

        )
    }
}
export default Form.create({name:"SearchForm"})(SearchForm)