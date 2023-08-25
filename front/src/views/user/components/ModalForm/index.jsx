import { Modal,Form,Input, Button ,Radio,DatePicker,Select,Cascader,Upload,Icon, message} from "antd";
import React, { Component } from "react";
import { userInfoParams } from "../../userInfoParams";
import { areaParams } from "../../areaParams";
import { getToken } from "@/utils/auth";
const { Option } = Select;
class ModalForm extends Component {
    state={
        selectValue:1,
        imageUrl:undefined,
        fileList:[]
    }
    /**
     * 级联更新后
     * @param {*} value 
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
        }
            
        
       
     };
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
                        this.props.form.getFieldDecorator(item.key,item.rule)( 
                            <Input placeholder={`请输入${item.title}`}></Input>
                        )}
                    </Form.Item>
                )
            case 'radio':
                return (<Form.Item label={item.title} key={index}  >{
                        this.props.form.getFieldDecorator(item.key,item.rule)( 
                            <Radio.Group onChange={this.onChange}>
                            <Radio value={1}>男</Radio>
                            <Radio value={2}>女</Radio>
                            
                            </Radio.Group>
                        )}
                </Form.Item>)
            case 'date':
                return (<Form.Item label={item.title} key={index}  >{
                        this.props.form.getFieldDecorator(item.key,item.rule)(
                            <DatePicker />
                        )}
                </Form.Item>)
            case 'cascader':
                return (<Form.Item label={item.title} key={index}  >{
                    this.props.form.getFieldDecorator(item.key,item.rule)(
                        <Cascader options={areaParams} onChange={this.onCascaderChange} showSearch="true" placeholder={`请选择${item.title}`} />
                    )}
            </Form.Item>)
            case 'select':
                return (<Form.Item label={item.title} key={index}>{
                    this.props.form.getFieldDecorator(item.key,item.rule)( 
                            <Select placeholder={`请选择${item.title}`}>
                                {
                                    this.props.roleList.map((item,index)=>{return <Option key={index} value={item.name}>{item.remark}</Option>})
                                }
                            </Select>
                        )}
                    </Form.Item>)
            case 'photo':
                return (
                    <Form.Item label={item.title} key={index}>{
                        this.props.form.getFieldDecorator(item.key,item.rule)( 
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
    getImgUrl=_=>{
        if(!!this.props.userModalFormData.avatar&&this.props.title.indexOf('修改')!=-1){
            this.setState(
                {imageUrl:this.props.userModalFormData.avatar}
            )
        }else{
            this.setState(
                {imageUrl:undefined}
            )
        }
    }

    componentWillReceiveProps(){
        this.getImgUrl()
        
    }
    render(){
        
        const { visible,title, onCancel, onOk, confirmLoading } = this.props;
        const formItemLayout = {
            labelCol: {
              sm: { span: 4 },
            },
            wrapperCol: {
              sm: { span: 16 },
            },
          };
            return (
                <Modal
                visible={visible}
                title={title}
                onCancel={onCancel}
                onOk={onOk}
                confirmLoading={confirmLoading}
                >
                    <Form {...formItemLayout} >
                    {
                         userInfoParams.map((item,index)=>{
                           return this.createFormItem(item,index)
                        })
                    }
                    </Form>
                </Modal>
            )
        
    }
}

export default Form.create({name:"ModalForm"})(ModalForm)