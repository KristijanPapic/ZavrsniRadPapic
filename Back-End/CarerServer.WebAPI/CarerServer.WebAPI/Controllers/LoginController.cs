using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using Carer.Service;
using Google.Apis.Auth;
using Google.Apis.Auth.OAuth2;

namespace CarerServer.WebAPI.Controllers
{
    public class LoginController : ApiController
    {
        ILoginService loginService;

        public LoginController(ILoginService loginService)
        {
            this.loginService = loginService;
        }
        public async Task<HttpResponseMessage> GetLogin(string idToken)
        {
 
            var validationSettings = new GoogleJsonWebSignature.ValidationSettings
            {
                Audience = new string[] { "255724298631-1vp1n1jmclak1meu9huqjifnmhfhkjfv.apps.googleusercontent.com" }

            };
            var payload = await GoogleJsonWebSignature.ValidateAsync(idToken, validationSettings);
            if(payload == null)
            {
                return Request.CreateResponse(System.Net.HttpStatusCode.NotFound);
            }

            return Request.CreateResponse(System.Net.HttpStatusCode.OK,await loginService.Login(payload));
            
        }
    }
}