import React, { Component } from "react";
import { Pagination } from "antd";
import '../../../../styles/user.less'
class TablePagination extends Component{
    state={
        // 分页组件值
        pageVal:{
            pageSize: 10,
            total: 500,
            pageNum: 1,
        }
    }
    /**
     * 修改每页条数
     * @param {*} current 当前页码
     * @param {*} size 最大容量
     */
    setPageSize=(current, size)=>{
        console.log('current, size',current, size);
        this.setState(
            ()=>{
                return { pageVal:{pageSize:size,pageNum:this.state.pageVal.pageNum,total:this.state.pageVal.total,}}
            },
            ()=>{
                console.log('this.state.pageVal=',this.state.pageVal);
                this.props.onShowSizeChange(this.state.pageVal)
            })
       
    }
    /**
     * 修改页码
     * @param {*} page 当前页码
     * @param {*} pageSize 最大容量
     */
    setPageNum=(page, pageSize)=>{
        console.log('page, pageSize',page, pageSize);
        this.setState(
            ()=>{
                return { pageVal:{pageSize:this.state.pageVal.pageSize,pageNum:page,total:this.state.pageVal.total,}}
            },
            ()=>{
                console.log('this.state.pageVal=',this.state.pageVal);
                this.props.onShowSizeChange(this.state.pageVal)
            })
    }
    /**
     * 修改总条目数
     * @param {*} total 传入的总条目数
     */
    setTotal=(total)=>{
        this.setState(
            ()=>{
                return { pageVal:{pageSize:this.state.pageVal.pageSize,pageNum:this.state.pageVal.pageNum,total:total,}}
            },
            ()=>{
            })
    }

    render(){
        return (
            <Pagination 
                className="table-pagination"
                showSizeChanger
                onShowSizeChange={this.setPageSize}
                onChange={this.setPageNum}
                total={this.state.pageVal.total}
                pageSize={this.state.pageVal.pageSize}
                current={this.state.pageVal.pageNum}
                pageSizeOptions={['1','2','5','10','50']}
            />
        )
    }
}
export default TablePagination