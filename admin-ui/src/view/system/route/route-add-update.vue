<template>
  <div>
    <Modal
      @on-ok="ok"
      @on-cancel="cancel"
      class="common-modal"
      :value="value">
      <p class="add-title">{{title}}</p>
      <div class="form-content">
        <Form ref="addUpdateForm" :model="addUpdateForm" :rules="addUpdateRules" :label-width="80">
          <FormItem prop="title" label="名称">
            <Input type="text" v-model="addUpdateForm.title" placeholder="名称" />
          </FormItem>
          <FormItem prop="type" label="类型">
            <RadioGroup v-model="addUpdateForm.type">
              <Radio :label="0">目录</Radio>
              <Radio :label="1">菜单</Radio>
              <Radio :label="2">按钮</Radio>
            </RadioGroup>
          </FormItem>
          <FormItem prop="pidName" label="上级">
            <Poptip trigger="click">
              <Input type="text" :readonly="true" v-model="addUpdateForm.pidName" placeholder="上级" :style="{width: '340px'}"/>
              <div slot="content" class="pid-poptip">
                <Tree :data="td" @on-select-change="treeChange"></Tree>
              </div>
            </Poptip>
          </FormItem>
          <FormItem prop="path" label="路由" v-if="addUpdateForm.type !== 2">
            <Input type="text" v-model="addUpdateForm.path" placeholder="路由" />
          </FormItem>
          <FormItem prop="name" label="路由名" v-if="addUpdateForm.type !== 2">
            <Input type="text" v-model="addUpdateForm.name" placeholder="路由名" />
          </FormItem>
          <FormItem prop="componentPath" label="路径" v-if="addUpdateForm.type !== 2">
            <Input type="text" v-model="addUpdateForm.componentPath" placeholder="路径" />
          </FormItem>
          <FormItem label="授权标识" v-if="addUpdateForm.type !== 0">
            <Input type="text" v-model="addUpdateForm.perms" placeholder="授权标识" />
          </FormItem>
          <FormItem label="图标" v-if="addUpdateForm.type !== 2">
            <Poptip trigger="click" popper-class="mod-menu__icon-popover">
              <Input type="text" :readonly="true" v-model="addUpdateForm.icon" :style="{width: '340px'}" class="icon-list__input" placeholder="图标" />
              <div class="mod-menu__icon-list" slot="content">
                  <Button v-for="(item, index) in iconList"
                           :key="index"
                           @click="iconActiveHandle(item)"
                           :class="{ 'is-active': item === addUpdateForm.icon }">
                    <common-icon :type="item" :size="18"></common-icon>
                  </Button>
              </div>
            </Poptip>
          </FormItem>
          <FormItem label="隐藏" v-if="addUpdateForm.type !== 2">
            <RadioGroup v-model="addUpdateForm.hideInMenu">
              <Radio :label="0">显示</Radio>
              <Radio :label="1">隐藏</Radio>
            </RadioGroup>
          </FormItem>
          <FormItem label="排序">
            <InputNumber v-model="addUpdateForm.orderNum" :min="0"></InputNumber>
          </FormItem>
        </Form>
      </div>
      <div slot="footer">
        <Button @click="cancel" type="info">取消</Button>
        <Button @click="ok" type="success">提交</Button>
      </div>
    </Modal>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
import CommonIcon from '_c/common-icon'
import iconNames from '@/libs/iconName'
export default {
  data () {
    return {
      iconList: [],
      td: [{
        id: 0,
        title: '根目录',
        expand: true,
        children: []
      }],
      addUpdateForm: {
        title: '',
        type: 1,
        pid: 0,
        pidName: '根目录',
        path: '',
        name: '',
        componentPath: '',
        hideInMenu: 0,
        icon: '',
        orderNum: 0
      },
      addUpdateRules: {
        title: [
          {
            required: true, message: '名称不能为空', trigger: 'blur'
          }
        ],
        type: [
          {
            required: true, type: 'number', message: '类型不能为空', trigger: 'change'
          }
        ],
        pidName: [
          {
            required: true, message: '上级不能为空', trigger: 'blur'
          }
        ],
        path: [
          {
            required: true, message: '路由不能为空', trigger: 'blur'
          }
        ],
        name: [
          {
            required: true, message: '路由名不能为空', trigger: 'blur'
          }
        ],
        componentPath: [
          {
            required: true, message: '路径不能为空', trigger: 'blur'
          }
        ]
      }
    }
  },
  props: {
    value: {
      type: Boolean,
      required: true,
      default: false
    },
    title: {
      type: String,
      default: '新增'
    }
  },
  components: {
    CommonIcon
  },
  mounted () {
    this.iconList = iconNames
  },
  methods: {
    ...mapActions(['saveRoute', 'updateRoute', 'getRouteById', 'getRouteSelectTree']),
    init (routeId) {
      this.getRouteSelectTree().then(res => {
        this.td[0].children = res.data.tree
      })
      if (routeId) {
        this.getRouteById({ routeId }).then(res => {
          this.addUpdateForm.id = res.data.route.id
          this.addUpdateForm.title = res.data.route.title
          this.addUpdateForm.type = res.data.route.type
          this.addUpdateForm.pid = res.data.route.pid
          this.addUpdateForm.path = res.data.route.path
          this.addUpdateForm.name = res.data.route.name
          this.addUpdateForm.componentPath = res.data.route.componentPath
          this.addUpdateForm.perms = res.data.route.perms
          this.addUpdateForm.icon = res.data.route.icon
          this.addUpdateForm.hideInMenu = res.data.route.hideInMenu
          this.addUpdateForm.orderNum = res.data.route.orderNum
          this.setPidName(this.td, res.data.route.pid)
        }).catch(err => {
          this.$Notice.error({
            title: '错误',
            desc: err.msg
          })
        })
      }
      this.$nextTick(() => {
        this.$refs.addUpdateForm.resetFields()
      })
    },
    cancel () {
      this.clearData()
      this.$emit('input', !this.value)
    },
    ok () {
      this.$refs.addUpdateForm.validate((valid) => {
        if (valid) {
          delete this.addUpdateForm.pidName
          let oper = this.addUpdateForm.id ? 'updateRoute' : 'saveRoute'
          this[oper](this.addUpdateForm).then(res => {
            if (res.data.code === 0) {
              this.$emit('input', !this.value)
              this.$emit('refreshDataList')
              this.clearData()
              this.$Notice.success({
                title: '成功',
                desc: '操作成功'
              })
            }
          }).catch(err => {
            this.$Notice.error({
              title: '错误',
              desc: err.msg
            })
          })
        } else {
          this.$Notice.warning({
            title: '警告',
            desc: '请完善信息'
          })
        }
      })
    },
    clearData () {
      this.addUpdateForm = {
        orderNum: 0,
        pid: 0,
        pidName: '根目录',
        type: 1,
        hideInMenu: 0
      }
    },
    iconActiveHandle (item) {
      this.$set(this.addUpdateForm, 'icon', item)
    },
    treeChange (value) {
      this.addUpdateForm.pid = value[0].id
      this.addUpdateForm.pidName = value[0].title
    },
    setPidName (treeData, pid) {
      for (let i = 0; i < treeData.length; i++) {
        if (treeData[i].children && treeData[i].children.length > 0) {
          if (treeData[i].id === pid) {
            this.addUpdateForm.pidName = treeData[i].title
            this.$set(treeData[i], 'selected', true)
            break
          } else {
            this.setPidName(treeData[i].children, pid)
          }
        } else {
          if (treeData[i].id === pid) {
            this.addUpdateForm.pidName = treeData[i].title
            this.$set(treeData[i], 'selected', true)
          }
        }
      }
    }
  }
}
</script>
<style lang="less">
  @import "~@/style/modal.less";
  .common-modal .form-content{
    padding: 20px 50px 20px 50px;
  }
  .ivu-radio-wrapper {
    font-size: 12px;
    vertical-align: middle;
    display: inline-block;
    position: relative;
    white-space: nowrap;
    margin-right: 42px;
    cursor: pointer;
}
.mod-menu {
  .menu-list__input,
  .icon-list__input {
    > .el-input__inner {
      cursor: pointer;
    }
  }
  &__icon-popover {
    max-width: 370px;
  }
  &__icon-list {
    word-wrap:break-word;
    word-break:normal;
    max-height: 180px;
    padding: 0;
    margin: -8px 0 0 -8px;
    > .ivu-btn {
      padding: 8px;
      margin: 8px 0 0 8px;
      > span {
        display: inline-block;
        vertical-align: middle;
        width: 18px;
        height: 18px;
        font-size: 18px;
      }
    }
  }
  .icon-list__tips {
    font-size: 18px;
    text-align: center;
    color: #e6a23c;
    cursor: pointer;
  }
}
.is-active{
  color: #15a193;
  border-color: #15a193;
}
.pid-poptip{
  height: 300px;
  overflow-x: hidden;
  overflow-y: auto;
}
.mod-menu__icon-list {
    width: 308px;
}
.mod-menu__icon-list > .ivu-btn {
    float: left;
    line-height: 1;
}
.mod-menu__icon-listbutton .ivu-btn{
  line-height: 1;
}
</style>
