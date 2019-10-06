<template>
  <div>
    <Card class="card-content">
      <p slot="title"
          class="card-content-title">
          <Row type="flex" justify="start" class="card-content-row">
            <Col span="12" class="card-content-add"  v-hasPermission="hasPermissions.add"><img @click="goToAdd()" src="@/assets/images/icon-add.png"></Col>
            <Col span="12" v-hasPermission="hasPermissions.delete"><img @click="confDel" src="@/assets/images/icon-delete.png"></Col>
        </Row>
      </p>
      <tree-table
        ref="treeTable"
        :data="data"
        :columns="columns"
        :border="true"
        :expand-type="false"
        :selectable="true">
        <template slot="type" slot-scope="scope">
          <Tag v-if="scope.row.type === 0" color="warning">目录</Tag>
          <Tag v-if="scope.row.type === 1" color="success">菜单</Tag>
          <Tag v-if="scope.row.type === 2" color="error">按钮</Tag>
        </template>
        <template slot="path" slot-scope="scope">
          <Tooltip v-if="scope.row.path" :content="scope.row.path" placement="top" class="ivu-table-cell-tooltip">
            <span class="ivu-table-cell-tooltip-content">
              {{scope.row.path}}
            </span>
          </Tooltip>
        </template>
        <template slot="name" slot-scope="scope">
          <Tooltip v-if="scope.row.name" :content="scope.row.name" placement="top" class="ivu-table-cell-tooltip">
            <span class="ivu-table-cell-tooltip-content">
              {{scope.row.name}}
            </span>
          </Tooltip>
        </template>
        <template slot="componentPath" slot-scope="scope">
          <Tooltip v-if="scope.row.componentPath" :content="scope.row.componentPath" placement="top" class="ivu-table-cell-tooltip">
            <span class="ivu-table-cell-tooltip-content">
              {{scope.row.componentPath}}
            </span>
          </Tooltip>
        </template>
        <template slot="perms" slot-scope="scope">
          <Tooltip v-if="scope.row.perms" :content="scope.row.perms" placement="top" class="ivu-table-cell-tooltip">
            <span class="ivu-table-cell-tooltip-content">
              {{scope.row.perms}}
            </span>
          </Tooltip>
        </template>
        <template slot="hideInMenu" slot-scope="scope">
          <Tag v-if="scope.row.hideInMenu == 1" color="error">是</Tag>
          <Tag v-else color="success">否</Tag>
        </template>
        <template slot="icon" slot-scope="scope">
          <common-icon v-if="scope.row.icon" :type="scope.row.icon" />
        </template>
        <template slot="operation" slot-scope="scope">
          <Button v-hasPermission="hasPermissions.update" type="warning" @click="goToAdd(scope.row.id)">编辑</Button>
        </template>
      </tree-table>
      <route-add-update
        v-model="addUpdate"
        :title="addUpdateTitle"
        ref="routeAddUpdate"
        @refreshDataList="initData">
      </route-add-update>
    </Card>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
import routeAddUpdate from './route-add-update'
import CommonIcon from '_c/common-icon'
export default {
  data () {
    return {
      addUpdate: false,
      addUpdateTitle: '新增',
      data: [],
      hasPermissions: {
        add: ['sys:role:save'],
        update: ['sys:role:update', 'sys:role:info'],
        delete: ['sys:role:delete']
      },
      columns: [
        {
          title: '名称',
          key: 'title',
          width: '200px',
          headerAlign: 'center'
        },
        {
          title: '类型',
          key: 'type',
          minWidth: '20px',
          align: 'center',
          headerAlign: 'center',
          type: 'template',
          template: 'type'
        },
        {
          title: '路由',
          key: 'path',
          minWidth: '20px',
          align: 'center',
          headerAlign: 'center',
          type: 'template',
          template: 'path'
        },
        {
          title: '路由名',
          key: 'name',
          minWidth: '20px',
          align: 'center',
          headerAlign: 'center',
          type: 'template',
          template: 'name'
        },
        {
          title: '路径',
          key: 'componentPath',
          minWidth: '50px',
          align: 'center',
          headerAlign: 'center',
          type: 'template',
          template: 'componentPath'
        },
        {
          title: '权限',
          key: 'perms',
          minWidth: '50px',
          align: 'center',
          headerAlign: 'center',
          type: 'template',
          template: 'perms'
        },
        {
          title: '隐藏',
          key: 'hideInMenu',
          minWidth: '20px',
          align: 'center',
          headerAlign: 'center',
          type: 'template',
          template: 'hideInMenu'
        },
        {
          title: '图标',
          key: 'icon',
          minWidth: '20px',
          align: 'center',
          headerAlign: 'center',
          type: 'template',
          template: 'icon'
        },
        {
          title: '排序',
          key: 'orderNum',
          minWidth: '20px',
          align: 'center',
          headerAlign: 'center'
        },
        {
          title: '操作',
          minWidth: '20px',
          align: 'center',
          headerAlign: 'center',
          type: 'template',
          template: 'operation'
        }
      ]
    }
  },
  components: {
    routeAddUpdate,
    CommonIcon
  },
  methods: {
    ...mapActions(['getRouteTree', 'deleteRoutes']),
    initData () {
      this.getRouteTree().then(res => {
        this.data = res.data.tree
      }).catch(err => {
        this.$Notice.error({
          title: '错误',
          desc: err.msg
        })
      })
    },
    goToAdd (id) {
      this.addUpdate = true
      this.addUpdateTitle = id ? '编辑' : '新增'
      this.$nextTick(() => {
        this.$refs.routeAddUpdate.init(id)
      })
    },
    confDel () {
      let deletes = this.$refs.treeTable.getCheckedProp('id')
      if (deletes.length > 0) {
        this.$Modal.confirm({
          title: '提示',
          content: '确认进行删除操作吗？',
          onOk: () => {
            this.deleteRoutes({ routeIds: deletes }).then(() => {
              this.initData()
              this.$Notice.success({
                title: '提示',
                desc: '删除成功'
              })
            }).catch(err => {
              this.$Notice.error({
                title: '错误',
                desc: err.msg
              })
            })
          },
          onCancel: () => {
            this.initData()
          }
        })
      } else {
        this.$Notice.warning({
          title: '警告',
          desc: '请至少选择一项'
        })
      }
    }
  },
  mounted () {
    this.initData()
  }
}
</script>
<style lang="less">
  @import "~@/style/style.less";
</style>
