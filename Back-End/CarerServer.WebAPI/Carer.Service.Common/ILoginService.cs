using Carer.Model;
using Google.Apis.Auth;
using System.Threading.Tasks;

namespace Carer.Service
{
    public interface ILoginService
    {
        Task<LoginResponseModel> Login(GoogleJsonWebSignature.Payload payload);
    }
}