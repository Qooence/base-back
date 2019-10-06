<template>
    <div>
      <Card class="card-search">
        <Form :model="searchParam" :label-width="80">
          <Row>
            <Col span='21'>
              <FormItem label="角色名">
                <Input v-model="searchParam.roleName" style="width:200px" placeholder="角色名" clearable></Input>
              </FormItem>
            </Col>
            <Col span='3' class=" card-search-search-btn-box" :label-width="10">
              <Button type="primary" @click="search">搜索</Button>
            </Col>
          </Row>
        </Form>
      </Card>
      <Card class="card-content">
        <p slot="title"
           class="card-content-title">
            <Row type="flex" justify="start" class="card-content-row">
              <Col span="12" class="card-content-add"  v-hasPermission="hasPermissions.add"><img @click="goToAdd" src="@/assets/images/icon-add.png"></Col>
              <Col span="12" v-hasPermission="hasPermissions.delete"><img @click="confDel" src="@/assets/images/icon-delete.png"></Col>
          </Row>
        </p>
        <div class="card-content-table">
          <Table border
            ref="selection"
            :loading="loading"
            :columns="colData"
            :data="listData"
            @on-selection-change="onChange">
          </Table>
        </div>
        <div class="card-content-pages">
          <Page :total="totalCount"
            show-elevator
            show-total
            :page-size="searchParam.limit"
            @on-change='change'
            @on-page-size-change='change'>
          </Page>
        </div>
      </Card>
      <role-add-update
        v-model="addUpdate"
        :title="addUpdateTitle"
        ref="roleAddUpdate"
        @refreshDataList="initData">
      </role-add-update>
    </div>
</template>
<script>
import { inOf } from '@/libs/tools'
import { mapActions, mapState } from 'vuex'
import roleAddUpdate from '@/view/system/role/role-add-update'
export default {
  data () {
    return {
      searchParam: {
        page: 1,
        limit: 10
      },
      hasPermissions: {
        add: ['sys:role:save'],
        update: ['sys:role:update', 'sys:role:info'],
        delete: ['sys:role:delete']
      },
      loading: true,
      addUpdate: false,
      showDetail: false,
      addUpdateTitle: '新增',
      roleData: [],
      colData: [
        {
          type: 'selection',
          width: 60,
          align: 'center'
        },
        {
          title: '角色名',
          key: 'roleName',
          tooltip: true,
          align: 'center'
        },
        {
          title: '备注',
          key: 'remark',
          tooltip: true,
          align: 'center'
        },
        {
          title: '创建时间',
          key: 'createTime',
          tooltip: true,
          align: 'center'
        },
        {
          title: '操作',
          width: 180,
          align: 'center',
          render: (h, params) => {
            let buttons = []
            if (this.permissionList.length &&
                inOf(this.permissionList, this.hasPermissions.update)) {
              buttons.push(
                h(
                  'Button',
                  {
                    props: {
                      type: 'warning'
                    },
                    style: {
                      margin: '0 5px'
                    },
                    on: {
                      click: () => {
                        this.addUpdate = true
                        this.addUpdateTitle = '编辑'
                        this.$nextTick(() => {
                          this.$refs.roleAddUpdate.init(params.row.roleId)
                        })
                      }
                    }
                  }, '编辑'
                )
              )
            }
            return h('div', buttons)
          }
        }
      ],
      listData: [],
      totalCount: 0,
      selectArry: []
    }
  },
  computed: {
    ...mapState({
      permissionList: state => state.router.permissions
    })
  },
  methods: {
    ...mapActions(['getRoleList', 'deleteRoles']),
    search () {
      this.searchParam.page = 1
      this.initData()
    },
    change (value) {
      this.searchParam.page = value
      this.initData()
    },
    onChange (value) {
      this.selectArry = value
    },
    goToAdd () {
      this.addUpdate = true
      this.addUpdateTitle = '新增'
      this.$nextTick(() => {
        this.$refs.roleAddUpdate.init()
      })
    },
    confDel () {
      if (this.selectArry.length > 0) {
        this.$Modal.confirm({
          title: '提示',
          content: '确认进行删除操作吗？',
          onOk: () => {
            this.deleteRoles({ roleIds: this.selectArry.map(item => item.roleId) }).then(() => {
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
            this.selectArry.length = 0
            this.$refs.selection.selectAll(false)
          }
        })
      } else {
        this.$Notice.warning({
          title: '警告',
          desc: '请至少选择一项'
        })
      }
    },
    initData () {
      this.loading = true
      this.getRoleList(this.searchParam).then(res => {
        this.loading = false
        this.listData = res.data.page.list
        this.totalCount = res.data.page.totalCount
      }).catch(err => {
        this.loading = false
        this.$Notice.error({
          title: '错误',
          desc: err.msg
        })
      })
    }
  },
  components: {
    roleAddUpdate
  },
  mounted () {
    this.initData()
  }
}
</script>
<style lang="less">
  @import "~@/style/style.less";
</style>
