using Carer.Model;
using Carer.Model.DomainModels;
using Carer.Repository.Common;
using Google.Apis.Auth;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Carer.Service
{
    public class LoginService : ILoginService
    {
        private IUserRepository userRepository;

        public LoginService(IUserRepository userRepository)
        {
            this.userRepository = userRepository;
        }
        public async Task<LoginResponseModel> Login(GoogleJsonWebSignature.Payload payload)
        {
            Guid id = await userRepository.GetUserByEmail(payload.Email);
            if (id == Guid.Empty)
            {
                
                UserDomainModel user = new UserDomainModel
                {
                    Id = Guid.NewGuid(),
                    Username = payload.Name,
                    Email = payload.Email,
                    PictureURL = payload.Picture,
                };
                await userRepository.PostUserAsync(user);
                return new LoginResponseModel {
                    IsNewUser = true,
                    UserId = user.Id
                };
            }
            return new LoginResponseModel { 
            IsNewUser = false,
            UserId = id
            };
        }
    }
}
