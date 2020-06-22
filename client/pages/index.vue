<template>
  <v-layout
    column
    justify-center
    align-center
  >
    <v-flex
      xs12
      sm8
      md6
    >
      <div class="text-center">
        <logo />
      </div>
      <v-card>
        <v-card-title class="headline">
          Send anonymous messages to your friends!
          <!-- <p>{{messageBody}}</p> -->
        </v-card-title>
        <v-card-text>
          <v-textarea
            name="input-7-1"
            :label="`Type your anonymous message to ${user} here!`"
            :hint="`Type your anonymous message to ${user} here!`"
            v-model="messageBody"
        ></v-textarea>
          <a
            href="#"
            rel="noopener noreferrer"
          >
            Terms and Conditions
          </a>
          <br>
          <a
            href="#"
            rel="noopener noreferrer"
          >
            Privacy Policy
          </a>
        </v-card-text>
        <v-card-actions>
          <v-btn
            color="primary"
            block
            nuxt
            :loading="isLoading"
            :disabled="isLoading"
            @click.prevent="sendMessage"
            to="#"
          >
            Send message anonymously
          </v-btn>
        </v-card-actions>
      </v-card>
      <!-- dialog starts -->
      <v-dialog
      v-model="openDialog"
      max-width="290"
    >
      <v-card>
        <v-card-title class="headline">Message sent successfully!</v-card-title>

        <v-card-text
          v-model="response"
          v-if="response"
        >
          {{ response.data.message }}
        </v-card-text>

        <v-card-actions>
          <v-btn
            color="green darken-1"
            text
            block
            @click="openDialog = false"
          >
            OK
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <!-- dialog ends -->
    </v-flex>
  </v-layout>
</template>

<script>
import Logo from '~/components/Logo.vue'

export default {
  mounted(){
    console.log(this.user)
  },
  components: {
    Logo
  },
  data() {
    return {
      isLoading: false,
      user: this.$route.query.user,
      messageBody: null,
      openDialog: false,
      response: null
    }
  },
  methods: {
    async sendMessage() {
      this.isLoading = true
      console.log(this.messageBody)
      await this.$axios.post('http://192.168.0.107:8000/api/send-message/', {
        username: this.user,
        message: this.messageBody
      })
      .then(res => {
        this.isLoading = false
        this.openDialog = true
        console.log(res)
        this.response = res
      })
      .catch(err => {
        this.isLoading = false
        console.log(err)
      })
    }
  }
}
</script>
